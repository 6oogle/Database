package __google_.util;

import java.util.function.Consumer;

public class Testing {
    public static <T> long test(Consumer<T> consumer, T object, int number){
        long end, start = System.nanoTime();
        for(int i = 0; i < number; i++)
            consumer.accept(object);
        end = System.nanoTime();
        return end - start;
    }
}
