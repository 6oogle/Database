package oogle.sync;

public interface Channel {
    void close();

    default void write(byte[] array){
        if(array == null)throw new IllegalArgumentException("Input array in channel is null");
        write(array, 0, array.length);
    }

    void write(byte[] array, int offset, int size);
}
