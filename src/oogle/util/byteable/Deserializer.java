package oogle.util.byteable;

import oogle.util.annotation.NotNull;

import java.util.UUID;

public interface Deserializer<T> {

    @NotNull
    T decode(@NotNull Decoder decoder);

    Deserializer<UUID> UUID = decoder -> new UUID(decoder.readLong(), decoder.readLong());

    Deserializer<boolean[]> BOOLEAN_ARRAY = decoder -> {
        int size = decoder.readInt();
        boolean[] array = new boolean[size];
        for (int i = 0; i < size; i++)
            array[i] = decoder.readBoolean();
        return array;
    };

    Deserializer<short[]> SHORT_ARRAY = decoder -> {
        int size = decoder.readInt();
        short[] array = new short[size];
        for (int i = 0; i < size; i++)
            array[i] = decoder.readShort();
        return array;
    };

    Deserializer<int[]> INT_ARRAY = decoder -> {
        int size = decoder.readInt();
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
            array[i] = decoder.readInt();
        return array;
    };

    Deserializer<long[]> LONG_ARRAY = decoder -> {
        int size = decoder.readInt();
        long[] array = new long[size];
        for (int i = 0; i < size; i++)
            array[i] = decoder.readLong();
        return array;
    };

    Deserializer<float[]> FLOAT_ARRAY = decoder -> {
        int size = decoder.readInt();
        float[] array = new float[size];
        for (int i = 0; i < size; i++)
            array[i] = decoder.readFloat();
        return array;
    };

    Deserializer<double[]> DOUBLE_ARRAY = decoder -> {
        int size = decoder.readInt();
        double[] array = new double[size];
        for (int i = 0; i < size; i++)
            array[i] = decoder.readDouble();
        return array;
    };
}
