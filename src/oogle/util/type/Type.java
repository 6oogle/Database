package oogle.util.type;

import oogle.util.byteable.Decoder;
import oogle.util.byteable.Encoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Type<T> {
    private final TypeEntry[] array;
    private final BiConsumer<T, Encoder> encoder;
    private final Function<Decoder, T> decoder;

    public Type(TypeEntry[] array, BiConsumer<T, Encoder> encoder, Function<Decoder, T> decoder) {
        this.array = array;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public void encode(Encoder encoder, T object) {
        this.encoder.accept(object, encoder);
    }

    public T decode(Decoder decoder) {
        return this.decoder.apply(decoder);
    }

    public int size() {
        return this.array.length;
    }

    public TypeEntry get(int index) {
        return this.array[index];
    }

    public TypeEntry[] array() {
        return this.array;
    }

    public boolean check(int index, Types type) {
        return this.array[index].type() == type;
    }

    public static <T> Type<T> of(String name, Types type, BiConsumer<T, Encoder> encoder, Function<Decoder, T> decoder) {
        return new Type(new TypeEntry[]{new TypeEntry(name, type)}, encoder, decoder);
    }

    public static Type<UUID> uuid(String one, String two) {
        return builder((uuid, encoder) -> {
            encoder.writeLong(uuid.getMostSignificantBits());
            encoder.writeLong(uuid.getLeastSignificantBits());
        }, decoder ->  new UUID(decoder.readLong(), decoder.readLong())
        ).add(one, Types.LONG).add(two, Types.LONG).build();
    }

    public static <T> Type.TypeBuilder<T> builder(BiConsumer<T, Encoder> encoder, Function<Decoder, T> decoder) {
        return new Type.TypeBuilder(encoder, decoder);
    }

    public static class TypeBuilder<T> {
        private final List<TypeEntry> entries = new ArrayList();
        private final BiConsumer<T, Encoder> encoder;
        private final Function<Decoder, T> decoder;

        public TypeBuilder(BiConsumer<T, Encoder> encoder, Function<Decoder, T> decoder) {
            this.encoder = encoder;
            this.decoder = decoder;
        }

        public Type.TypeBuilder<T> add(String name, Types type) {
            if (name == null) {
                throw new IllegalArgumentException("name is null");
            } else if (type == null) {
                throw new IllegalArgumentException("type is null");
            } else {
                this.entries.add(new TypeEntry(name, type));
                return this;
            }
        }

        public Type<T> build() {
            return new Type<>(this.entries.toArray(new TypeEntry[0]), this.encoder, this.decoder);
        }
    }
}
