package __google_;

import __google_.crypt.async.RSA;

public class Main{
    public static void main(String[] args) {
        RSA rsa = new RSA(2048);
        System.out.println(rsa.getBytePublicKey().length);
        System.out.println(rsa.encodeByte(new byte[128]).length);
    }
}
