package oogle.util.byteable;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FastEncoder implements Encoder{
    private ByteList bytes = new ByteList();

    @Override
    public Encoder writeBytes(byte[] array) {
        writeInt(array.length);
        for(int i = 0; i < array.length; i++)
            bytes.add(array[i]);
        return this;
    }

    @Override
    public Encoder writeBoolean(boolean b) {
        writeByte((byte)(b ? 1 : 0));
        return this;
    }

    @Override
    public Encoder writeByte(byte b) {
        bytes.add(b);
        return this;
    }

    @Override
    public Encoder writeShort(short s) {
        bytes.add((byte)(s >> 8));
        bytes.add((byte)s);
        return this;
    }

    @Override
    public Encoder writeInt(int i) {
        bytes.add((byte)(i >> 24));
        bytes.add((byte)(i >> 16));
        bytes.add((byte)(i >> 8));
        bytes.add((byte)i);
        return this;
    }

    @Override
    public Encoder writeLong(long l) {
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
    public Encoder writeFloat(float f) {
        writeInt(Float.floatToIntBits(f));
        return this;
    }

    @Override
    public Encoder writeDouble(double d) {
        writeLong(Double.doubleToLongBits(d));
        return this;
    }

    @Override
    public Encoder writeString(String s) {
        writeBytes(s.getBytes(StandardCharsets.UTF_8));
        return this;
    }

    @Override
    public byte[] generate() {
        return Arrays.copyOf(bytes.elementData, bytes.size);
    }

    @Override
    public int generate(byte[] array, int offset) {
        System.arraycopy(bytes.elementData, 0, array, offset, bytes.size);
        return bytes.size;
    }
}
