package oogle.util.byteable;

import oogle.util.Type;

public class TypeDecoder implements Decoder{
    private final Type type;
    private final int size;
    private final Decoder source;
    private int index;

    public TypeDecoder(Type type, Decoder source){
        this.type = type;
        this.size = type.size();
        this.source = source;
    }

    @Override
    public byte[] readBytes() {
        if(size == index) outSize();
        if(!type.checkBytes(index)) incorrectType("BYTES");
        index++;
        return source.readBytes();
    }

    @Override
    public boolean readBoolean() {
        if(size == index) outSize();
        if(!type.checkBoolean(index)) incorrectType("BOOLEAN");
        index++;
        return source.readBoolean();
    }

    @Override
    public byte readByte() {
        if(size == index) outSize();
        if(!type.checkByte(index)) incorrectType("BYTE");
        index++;
        return source.readByte();
    }

    @Override
    public short readShort() {
        if(size == index) outSize();
        if(!type.checkShort(index)) incorrectType("SHORT");
        index++;
        return source.readShort();
    }

    @Override
    public int readInt() {
        if(size == index) outSize();
        if(!type.checkInt(index)) incorrectType("INT");
        index++;
        return source.readInt();
    }

    @Override
    public long readLong() {
        if(size == index) outSize();
        if(!type.checkLong(index)) incorrectType("LONG");
        index++;
        return source.readLong();
    }

    @Override
    public float readFloat() {
        if(size == index) outSize();
        if(!type.checkFloat(index)) incorrectType("FLOAT");
        index++;
        return source.readFloat();
    }

    @Override
    public double readDouble() {
        if(size == index) outSize();
        if(!type.checkDouble(index)) incorrectType("DOUBLE");
        index++;
        return source.readDouble();
    }

    @Override
    public String readStr() {
        if(size == index) outSize();
        if(!type.checkString(index)) incorrectType("STRING");
        index++;
        return source.readStr();
    }

    private void outSize(){
        throw new ArrayIndexOutOfBoundsException("Read over type, max " + size);
    }

    private void incorrectType(String called){
        throw new IllegalArgumentException("Read incorrect type, called " + called + ", need " + type.getName(index));
    }
}
