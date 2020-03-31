package oogle.util.byteable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface Serializable<V> {
    void encode(V object, Encoder encoder);

    V decode(Decoder decoder);

    static <T> Serializable<T> get(BiConsumer<T, Encoder> encoder, Function<Decoder, T> decoder){
        return new Serializable<T>() {
            @Override
            public void encode(T object, Encoder e) {
                encoder.accept(object, e);
            }

            @Override
            public T decode(Decoder d) {
                return decoder.apply(d);
            }
        };
    }
}
