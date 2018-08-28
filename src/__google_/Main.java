package __google_;

import __google_.crypt.Certificate;

public class Main{

    public static void main(String[] args){
        Certificate certificate = new Certificate(1536);
        System.out.println(certificate.getBytePublicKey().length);
        System.out.println(certificate.getBytePrivateKey().length);
        System.out.println();
    }
}
