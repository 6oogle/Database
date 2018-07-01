package __google_;

import __google_.crypt.AES;
import __google_.crypt.Blowfish;
import __google_.crypt.Crypt;
import __google_.crypt.RSA;
import __google_.io.FileIO;
import __google_.net.Client;
import __google_.net.Server;
import __google_.util.Fast;

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

    public static void AES(){
        //Key size only 16 | 24 | 32
        defCrypt(new AES("LolLolLolLolLolL"));
    }

    public static void RSA(){
        //Async crypt
        defCrypt(new RSA());
    }

    public static void Blowfish(){
        //Custom key size
        defCrypt(new Blowfish("LolLolLolLolLolLaff"));
    }

    public static void fastLowerEN(){
        String lineEN = "aAbBcCdDeEfF123456@@%";

        System.out.println(Fast.toLowerEN(lineEN));
        System.out.println("Fast -> " + Testing.test((l) -> Fast.toLowerEN(l), lineEN, iterations));
        System.out.println(lineEN.toLowerCase());
        System.out.println("Default -> " + Testing.test((l) -> l.toLowerCase(), lineEN, iterations));
    }

    public static void fastUpperEN(){
        String lineEN = "aAbBcCdDeEfF123456@@%";

        System.out.println(Fast.toUpperEN(lineEN));
        System.out.println("Fast -> " + Testing.test((l) -> Fast.toUpperEN(l), lineEN, iterations));
        System.out.println(lineEN.toUpperCase());
        System.out.println("Default -> " + Testing.test((l) -> l.toUpperCase(), lineEN, iterations));
    }

    public static void fastLowerRU(){
        String lineRU = "аАбБвВгГдДеЕёЁ123456@@%";

        System.out.println(Fast.toLowerRU(lineRU));
        System.out.println("Fast -> " + Testing.test((l) -> Fast.toLowerRU(l), lineRU, iterations));
        System.out.println(lineRU.toLowerCase());
        System.out.println("Default -> " + Testing.test((l) -> l.toLowerCase(), lineRU, iterations));
    }

    public static void fastUpperRU(){
        String lineRU = "аАбБвВгГдДеЕёЁ123456@@%";

        System.out.println(Fast.toUpperRU(lineRU));
        System.out.println("Fast -> " + Testing.test((l) -> Fast.toUpperRU(l), lineRU, iterations));
        System.out.println(lineRU.toUpperCase());
        System.out.println("Default -> " + Testing.test((l) -> l.toUpperCase(), lineRU, iterations));
    }

    public static void net(){
        Server server = new Server(4000, (thr) -> {
            String line = thr.read();
            System.out.println("Input -> " + line);
            thr.write(line);
            thr.close();
        });
        server.start();

        Client client = new Client("localhost", 4000);
        client.connect();
        client.send("LolKek");
        System.out.println("Client input -> " + client.read());
        client.close();
        server.close();
    }

    public static void file(){
        FileIO.write("Lol", "Lol12355");
        System.out.println(FileIO.read("Lol"));
    }

    private static void defCrypt(Crypt crypt){
        String line = "Lol 12355";
        String encoded = crypt.encode(line);

        System.out.println(line);
        System.out.println(encoded);
        System.out.println(crypt.decode(encoded));
    }
}
