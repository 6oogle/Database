package __google_;

import __google_.crypt.AES;
import __google_.crypt.Crypt;

public class Main {

    public static void main(String[] args) {
        Crypt crypt = new AES("LolLolLolLolLolL");

        String line = "Lol 12355";
        String encoded = crypt.encode(line);

        System.out.println(line);
        System.out.println(encoded);
        System.out.println(crypt.decode(encoded));
    }
}
