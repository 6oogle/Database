package oogle.util.byteable;

import oogle.util.annotation.NotNull;

import java.util.List;
import java.util.Map;

public interface Encoder {

    @NotNull
    default Encoder writeRaw(@NotNull byte[] array) {
        if (array == null) throw new IllegalArgumentException("Input array in encoder is null");
        return writeRaw(array, 0, array.length);
    }

    @NotNull
    Encoder writeRaw(@NotNull byte[] array, int offset, int size);

    @NotNull
    default Encoder writeBytes(@NotNull byte[] array) {
        if (array == null) throw new IllegalArgumentException("Input array in encoder is null");
        return writeBytes(array, 0, array.length);
    }

    @NotNull
    Encoder writeBytes(@NotNull byte[] array, int offset, int size);

    @NotNull
    Encoder writeBoolean(boolean b);

    @NotNull
    Encoder writeByte(byte b);

    @NotNull
    Encoder writeShort(short s);

    @NotNull
    Encoder writeInt(int i);

    @NotNull
    Encoder writeLong(long l);

    @NotNull
    Encoder writeFloat(float f);

    @NotNull
    Encoder writeDouble(double d);

    @NotNull
    Encoder writeString(@NotNull String s);

    @NotNull
    default Encoder write(@NotNull Byteable b) {
        b.encode(this);
        return this;
    }

    @NotNull
    default <T> Encoder write(@NotNull T object, @NotNull Serializer<T> serializer) {
        serializer.encode(object, this);
        return this;
    }

    @NotNull
    default <T> Encoder writeArray(@NotNull T[] array, @NotNull Serializer<T> serializer) {
        writeInt(array.length);
        for (T obj : array) write(obj, serializer);
        return this;
    }

    @NotNull
    default <T extends Byteable> Encoder writeArray(@NotNull T[] array) {
        return writeArray(array, Byteable::encode);
    }

    @NotNull
    default Encoder writeArray(@NotNull String[] array) {
        return writeArray(array, (string, encoder) -> encoder.writeString(string));
    }

    @NotNull
    default <T> Encoder writeList(@NotNull List<T> list, @NotNull Serializer<T> consumer) {
        writeInt(list.size());
        for (T obj : list) write(obj, consumer);
        return this;
    }

    @NotNull
    default <T extends Byteable> Encoder writeList(@NotNull List<T> list) {
        return writeList(list, Byteable::encode);
    }

    @NotNull
    default <K, V> Encoder writeMap(@NotNull Map<K, V> map, @NotNull Serializer<K> key, @NotNull Serializer<V> value) {
        writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            write(entry.getKey(), key);
            write(entry.getValue(), value);
        }
        return this;
    }

    @NotNull
    default <K, V extends Byteable> Encoder writeMapCustomKeyMap(@NotNull Map<K, V> map, @NotNull Serializer<K> key) {
        return writeMap(map, key, Byteable::encode);
    }

    @NotNull
    default <K extends Byteable, V> Encoder writeMapCustomValue(@NotNull Map<K, V> map, @NotNull Serializer<V> value) {
        return writeMap(map, Byteable::encode, value);
    }

    @NotNull
    default <K extends Byteable, V extends Byteable> Encoder writeMap(@NotNull Map<K, V> map) {
        return writeMap(map, Byteable::encode, Byteable::encode);
    }
}
