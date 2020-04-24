package oogle.util.byteable;

import oogle.util.annotation.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

public interface Decoder {

    @NotNull
    default byte[] readRaw(int size) {
        return readRaw(new byte[size], 0, size);
    }

    @NotNull
    byte[] readRaw(byte[] array, int offset, int size);

    @NotNull
    byte[] readBytes();

    boolean readBoolean();

    byte readByte();

    short readShort();

    int readInt();

    long readLong();

    float readFloat();

    double readDouble();

    @NotNull
    String readString();

    boolean hasNext();

    @NotNull
    default <T> T read(@NotNull Deserializer<T> deserializer) {
        return deserializer.decode(this);
    }

    @NotNull
    default <T> T[] readArray(@NotNull IntFunction<T[]> constructor, @NotNull Deserializer<T> deserializer) {
        int size = readInt();
        T[] array = constructor.apply(size);
        for (int i = 0; i < size; i++)
            array[i] = read(deserializer);
        return array;
    }

    @NotNull
    default <T> List<T> readList(@NotNull Deserializer<T> constructor) {
        int size = readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) list.add(read(constructor));
        return list;
    }

    @NotNull
    default <K, V> Map<K, V> readMap(@NotNull Deserializer<K> key, @NotNull Deserializer<V> value) {
        int size = readInt();
        Map<K, V> map = new HashMap<>(size);
        for (int i = 0; i < size; i++)
            map.put(read(key), read(value));
        return map;
    }
}
