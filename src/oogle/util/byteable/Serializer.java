package oogle.util.byteable;

import oogle.util.annotation.NotNull;

import java.util.UUID;

public interface Serializer<T> {

    void encode(@NotNull T object, @NotNull Encoder encoder);

    Serializer<UUID> UUID = (uuid, encoder) -> encoder
            .writeLong(uuid.getMostSignificantBits())
            .writeLong(uuid.getLeastSignificantBits());

    Serializer<boolean[]> BOOLEAN_ARRAY = (array, encoder) -> {
        encoder.writeInt(array.length);
        for (boolean b : array) encoder.writeBoolean(b);
    };

    Serializer<short[]> SHORT_ARRAY = (array, encoder) -> {
        encoder.writeInt(array.length);
        for (short value : array) encoder.writeShort(value);
    };

    Serializer<int[]> INT_ARRAY = (array, encoder) -> {
        encoder.writeInt(array.length);
        for (int value : array) encoder.writeInt(value);
    };

    Serializer<long[]> LONG_ARRAY = (array, encoder) -> {
        encoder.writeInt(array.length);
        for (long l : array) encoder.writeLong(l);
    };

    Serializer<float[]> FLOAT_ARRAY = (array, encoder) -> {
        encoder.writeInt(array.length);
        for (float v : array) encoder.writeFloat(v);
    };

    Serializer<double[]> DOUBLE_ARRAY = (array, encoder) -> {
        encoder.writeInt(array.length);
        for (double v : array) encoder.writeDouble(v);
    };
}
