package __google_;

import __google_.util.ByteUnzip;
import __google_.util.ByteZip;
import __google_.util.Coder;

import java.util.function.Consumer;

public class Testing {
    private static final int iterations = 100000;

    public static <T> long test(Consumer<T> consumer, T object, int number){
        long end, start = System.nanoTime();
        for(int i = 0; i < number; i++)
            consumer.accept(object);
        end = System.nanoTime();
        return end - start;
    }

    public static void fastLowerEN(){
        String lineEN = "aAbBcCdDeEfF123456@@%";

        System.out.println(Coder.toLowerEN(lineEN));
        System.out.println("Fast -> " + Testing.test((l) -> Coder.toLowerEN(l), lineEN, iterations));
        System.out.println(lineEN.toLowerCase());
        System.out.println("Default -> " + Testing.test((l) -> l.toLowerCase(), lineEN, iterations));
    }

    public static void fastUpperEN(){
        String lineEN = "aAbBcCdDeEfF123456@@%";

        System.out.println(Coder.toUpperEN(lineEN));
        System.out.println("Fast -> " + Testing.test((l) -> Coder.toUpperEN(l), lineEN, iterations));
        System.out.println(lineEN.toUpperCase());
        System.out.println("Default -> " + Testing.test((l) -> l.toUpperCase(), lineEN, iterations));
    }

    public static void fastLowerRU(){
        String lineRU = "аАбБвВгГдДеЕёЁ123456@@%";

        System.out.println(Coder.toLowerRU(lineRU));
        System.out.println("Fast -> " + Testing.test((l) -> Coder.toLowerRU(l), lineRU, iterations));
        System.out.println(lineRU.toLowerCase());
        System.out.println("Default -> " + Testing.test((l) -> l.toLowerCase(), lineRU, iterations));
    }

    public static void fastUpperRU(){
        String lineRU = "аАбБвВгГдДеЕёЁ123456@@%";

        System.out.println(Coder.toUpperRU(lineRU));
        System.out.println("Fast -> " + Testing.test((l) -> Coder.toUpperRU(l), lineRU, iterations));
        System.out.println(lineRU.toUpperCase());
        System.out.println("Default -> " + Testing.test((l) -> l.toUpperCase(), lineRU, iterations));
    }

    public static void bytezip(){
        ByteZip zip = new ByteZip();
        zip.add("LolKekддд").add(123).add(12512552512512L);
        ByteUnzip unzip = new ByteUnzip(zip.build());
        System.out.println(unzip.getString());
        System.out.println(unzip.getInt());
        System.out.println(unzip.getLong());
    }
}
