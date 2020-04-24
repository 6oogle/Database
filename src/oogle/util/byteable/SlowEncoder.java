package oogle.util.byteable;

import java.nio.charset.StandardCharsets;

public class SlowEncoder implements BEncoder {

    private final ByteList bytes = new ByteList(), metadatas = new ByteList();
    private byte metadata;
    private byte bitMetadata;

    @Override
    public Encoder writeRaw(byte[] array, int offset, int size) {
        bytes.add(array, offset, size);
        return this;
    }

    @Override
    public Encoder writeBytes(byte[] array, int offset, int size) {
        writeInt(size);
        bytes.add(array, offset, size);
        return this;
    }

    @Override
    public Encoder writeBoolean(boolean b) {
        if(bitMetadata == 7){
            metadatas.add(metadata);
            metadata = 0;
            bitMetadata = 0;
        }
        if(b) metadata |= (byte)(1 << bitMetadata);
        bitMetadata++;
        return this;
    }

    @Override
    public Encoder writeByte(byte b) {
        bytes.add(b);
        return this;
    }

    private boolean useCompressOfShort = false;

    public SlowEncoder setUseCompressOfShort(boolean b) {
        useCompressOfShort = b;
        return this;
    }

    @Override
    public Encoder writeShort(short s) {
        if(useCompressOfShort){
            boolean bool = ((s & 0xFFFF) >>> 8) == s;
            writeBoolean(bool);
            if(bool){
                bytes.add((byte)s);
                return this;
            }
        }
        bytes.add((byte)(s >> 8));
        bytes.add((byte)s);
        return this;
    }

    private int sizeCompressOfInt = -1;

    public SlowEncoder setSizeCompressOfInt(int i) {
        sizeCompressOfInt = i;
        return this;
    }

    @Override
    public Encoder writeInt(int i) {
        if(sizeCompressOfInt != -1){
            boolean b = i >>> (sizeCompressOfInt << 3) == i;
            writeBoolean(b);
            if(b){
                for(int j = 0; j < sizeCompressOfInt; j++)
                    bytes.add((byte)(i >>> (j << 3)));
                return this;
            }
        }
        writeIntDirectly(i);
        return this;
    }

    private int sizeCompressOfLong = -1;

    public SlowEncoder setSizeCompressOfLong(int i){
        sizeCompressOfLong = i;
        return this;
    }

    @Override
    public Encoder writeLong(long l) {
        if(sizeCompressOfLong != -1){
            boolean b = l >>> (sizeCompressOfLong << 3) == l;
            writeBoolean(b);
            if(b){
                for(int j = 0; j < sizeCompressOfInt; j++)
                    bytes.add((byte)(l >>> (j << 3)));
                return this;
            }
        }
        writeLongDirectly(l);
        return this;
    }

    private boolean writeFloatDirectly = true;

    public SlowEncoder setWriteFloatDirectly(boolean b) {
        writeFloatDirectly = b;
        return this;
    }

    @Override
    public Encoder writeFloat(float f) {
        int i = Float.floatToIntBits(f);
        if(writeFloatDirectly)writeIntDirectly(i);
        else writeInt(i);
        return this;
    }

    private boolean writeDoubleDirectly = true;

    public SlowEncoder setWriteDoubleDirectly(boolean b) {
        writeDoubleDirectly = b;
        return this;
    }

    @Override
    public Encoder writeDouble(double d) {
        long l = Double.doubleToLongBits(d);
        if(writeDoubleDirectly)writeLongDirectly(l);
        else writeLong(l);
        return this;
    }

    private boolean usingCompressACSII = false;

    public SlowEncoder setUsingCompressACSII(boolean b){
        usingCompressACSII = b;
        return this;
    }

    @Override
    public Encoder writeString(String s) {
        if(usingCompressACSII){
            boolean isACSII = true;
            for(char c : s.toCharArray())
                if(c > 0xFF){
                    isACSII = false;
                    break;
                }
            writeBoolean(usingCompressACSII);
            if(isACSII){
                writeBytes(s.getBytes(StandardCharsets.US_ASCII));
                return this;
            }
        }
        writeBytes(s.getBytes(StandardCharsets.UTF_8));
        return this;
    }

    @Override
    public byte[] generate() {
        if(bitMetadata != 0){
            metadatas.add(metadata);
            bitMetadata = 0;
        }
        byte array[] = new byte[metadatas.size + bytes.size + 2];
        generate(array, 0);
        return array;
    }

    @Override
    public int generate(byte[] array, int offset) {
        if(bitMetadata != 0)metadatas.add(metadata);
        array[offset++] = (byte)(metadatas.size >>> 8);
        array[offset++] = (byte)metadatas.size;
        System.arraycopy(metadatas.elementData, 0, array, offset, metadatas.size);
        System.arraycopy(bytes.elementData, 0, array, offset + metadatas.size, bytes.size);
        return metadatas.size + bytes.size + 2;
    }

    private void writeIntDirectly(int i){
        bytes.add((byte)(i >> 24));
        bytes.add((byte)(i >> 16));
        bytes.add((byte)(i >> 8));
        bytes.add((byte)i);
    }

    private void writeLongDirectly(long l){
        bytes.add((byte)(l >> 56));
        bytes.add((byte)(l >> 48));
        bytes.add((byte)(l >> 40));
        bytes.add((byte)(l >> 32));
        bytes.add((byte)(l >> 24));
        bytes.add((byte)(l >> 16));
        bytes.add((byte)(l >> 8));
        bytes.add((byte)l);
    }

    public static SlowEncoder defaultEncoder(){
        return new SlowEncoder().setSizeCompressOfInt(2).setSizeCompressOfLong(6).setUsingCompressACSII(true).setUseCompressOfShort(true);
    }
}
