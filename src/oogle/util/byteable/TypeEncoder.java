package oogle.util.byteable;

import oogle.util.Type;

public class TypeEncoder implements Encoder{
    private final Type type;
    private final int size;
    private final Encoder source;
    private int index;

    public TypeEncoder(Type type, Encoder source){
        this.type = type;
        this.size = type.size();
        this.source = source;
    }

    @Override
    public Encoder writeBytes(byte[] array) {
        if(size == index) outSize();
        if(!type.checkBytes(index)) incorrectType("BYTES");
        index++;
        source.writeBytes(array);
        return this;
    }

    @Override
    public Encoder writeBoolean(boolean b) {
        if(size == index) outSize();
        if(!type.checkBoolean(index)) incorrectType("BOOLEAN");
        index++;
        source.writeBoolean(b);
        return this;
    }

    @Override
    public Encoder writeByte(byte b) {
        if(size == index) outSize();
        if(!type.checkByte(index)) incorrectType("BYTE");
        index++;
        source.writeByte(b);
        return this;
    }

    @Override
    public Encoder writeShort(short s) {
        if(size == index) outSize();
        if(!type.checkShort(index)) incorrectType("SHORT");
        index++;
        source.writeShort(s);
        return this;
    }

    @Override
    public Encoder writeInt(int i) {
        if(size == index) outSize();
        if(!type.checkInt(index)) incorrectType("INT");
        index++;
        source.writeInt(i);
        return this;
    }

    @Override
    public Encoder writeLong(long l) {
        if(size == index) outSize();
        if(!type.checkLong(index)) incorrectType("LONG");
        index++;
        source.writeLong(l);
        return this;
    }

    @Override
    public Encoder writeFloat(float f) {
        if(size == index) outSize();
        if(!type.checkFloat(index)) incorrectType("FLOAT");
        index++;
        source.writeFloat(f);
        return this;
    }

    @Override
    public Encoder writeDouble(double d) {
        if(size == index) outSize();
        if(!type.checkDouble(index)) incorrectType("DOUBLE");
        index++;
        source.writeDouble(d);
        return this;
    }

    @Override
    public Encoder writeString(String s) {
        if(size == index) outSize();
        if(!type.checkString(index)) incorrectType("STRING");
        index++;
        source.writeString(s);
        return this;
    }

    @Override
    public byte[] generate() {
        if(size != index)throw new IllegalArgumentException("Write " + index + ", need " + size);
        return source.generate();
    }

    @Override
    public int generate(byte[] array, int offset) {
        if(size != index)throw new IllegalArgumentException("Write " + index + ", need " + size);
        return source.generate(array, offset);
    }

    private void outSize(){
        throw new ArrayIndexOutOfBoundsException("Write over type, max " + size);
    }

    private void incorrectType(String called){
        throw new IllegalArgumentException("Write incorrect type, called " + called + ", need " + type.getName(index));
    }
}
