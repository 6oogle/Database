package oogle.util.byteable;

public interface BEncoder extends Encoder{
    byte[] generate();

    int generate(byte[] array, int offset);
}
