package __google_;

import __google_.crypt.async.RSA;

public class Main{
    public static void main(String[] args){
        String pass = "LolKek";
        RSA rsa = RSA.generate(pass, 2048, 12);
        String line = rsa.encode("asffa");
        System.out.println(line);
        rsa = RSA.generate(pass, 2048, 12);
        System.out.println(rsa.decode(line));
    }
}
