package __google_;

import __google_.crypt.AES;
import __google_.crypt.Blowfish;
import __google_.crypt.Crypt;
import __google_.crypt.RSA;
import __google_.net.Client;
import __google_.net.Server;

public class Main {

    public static void main(String[] args) {
        RSA();
    }

    public static void AES(){
        defCrypt(new AES("LolLolLolLolLolL"));
    }

    public static void RSA(){
        defCrypt(new RSA());
    }

    public static void Blowfish(){
        defCrypt(new Blowfish("LolLolLolLolLolL"));
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

    private static void defCrypt(Crypt crypt){
        String line = "Lol 12355";
        String encoded = crypt.encode(line);

        System.out.println(line);
        System.out.println(encoded);
        System.out.println(crypt.decode(encoded));
    }
}
