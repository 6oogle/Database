package oogle.util.byteable;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FastEncoder implements BEncoder {
    private final ByteList bytes;

    public FastEncoder(int initialCapacity){
        bytes = new ByteList(initialCapacity);
    }

    public FastEncoder(){
        this(10);
    }

    @Override
    public FastEncoder writeBytes(byte[] array, int offset, int size) {
        writeInt(size);
        bytes.add(array, offset, size);
        return this;
    }

    @Override
    public FastEncoder writeBoolean(boolean b) {
        writeByte((byte)(b ? 1 : 0));
        return this;
    }

    @Override
    public FastEncoder writeByte(byte b) {
        bytes.add(b);
        return this;
    }

    @Override
    public FastEncoder writeShort(short s) {
        bytes.add((byte)(s >> 8));
        bytes.add((byte)s);
        return this;
    }

    @Override
    public FastEncoder writeInt(int i) {
        bytes.add((byte)(i >> 24));
        bytes.add((byte)(i >> 16));
        bytes.add((byte)(i >> 8));
        bytes.add((byte)i);
        return this;
    }

    @Override
    public FastEncoder writeLong(long l) {
        bytes.add((byte)(l >> 56));
        bytes.add((byte)(l >> 48));
        bytes.add((byte)(l >> 40));
        bytes.add((byte)(l >> 32));
        bytes.add((byte)(l >> 24));
        bytes.add((byte)(l >> 16));
        bytes.add((byte)(l >> 8));
        bytes.add((byte)l);
        return this;
    }

    @Override
    public FastEncoder writeFloat(float f) {
        writeInt(Float.floatToIntBits(f));
        return this;
    }

    @Override
    public FastEncoder writeDouble(double d) {
        writeLong(Double.doubleToLongBits(d));
        return this;
    }

    @Override
    public FastEncoder writeString(String s) {
        writeBytes(s.getBytes(StandardCharsets.UTF_8));
        return this;
    }

    @Override
    public byte[] generate() {
        if(bytes.elementData.length == bytes.size)return bytes.elementData;
        return Arrays.copyOf(bytes.elementData, bytes.size);
    }

    @Override
    public int generate(byte[] array, int offset) {
        System.arraycopy(bytes.elementData, 0, array, offset, bytes.size);
        return bytes.size;
    }
}
