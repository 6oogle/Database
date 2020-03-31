package oogle.util.byteable;

public interface Encoder {
    default Encoder writeBytes(byte array[]){
        if(array == null) throw new IllegalArgumentException("Input array in encoder is null");
        return writeBytes(array, 0, array.length);
    }

    Encoder writeBytes(byte array[], int offset, int size);

    Encoder writeBoolean(boolean b);

    Encoder writeByte(byte b);

    Encoder writeShort(short s);

    Encoder writeInt(int i);

    Encoder writeLong(long l);

    Encoder writeFloat(float f);

    Encoder writeDouble(double d);

    Encoder writeString(String s);
}
