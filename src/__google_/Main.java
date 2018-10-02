package __google_;

import __google_.crypt.async.RSA;

import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        String password = "LolKek";
        RSA rsa = RSA.generate(password, 2048, 12);
        String encoded = rsa.encode(password);
        byte hash[] = rsa.getHashPrivateKey();
        rsa = RSA.generate(password, 2048, 12);
        System.out.println(Arrays.equals(rsa.getHashPrivateKey(), hash));
        System.out.println(encoded);
        System.out.println(rsa.decode(encoded));
    }
}
