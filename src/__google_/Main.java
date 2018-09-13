package __google_;

import __google_.crypt.async.Certificate;
import __google_.crypt.async.SignedCertificate;
import __google_.io.FileIO;
import __google_.util.Byteable;

public class Main{
    public static void main(String[] args){
        Certificate privateCertificate = new Certificate(FileIO.readBytes("DelfikPro/key.private"), null);
        SignedCertificate singed = Byteable.toByteable(FileIO.readBytes("DelfikPro/signed.public"), SignedCertificate.class);
        System.out.println(singed.checkCertificate());
        String line = "LolKek";
        System.out.println(line);
        String encoded = privateCertificate.encode(line);
        System.out.println(encoded);
        String decoded = singed.getCertificate().decode(encoded);
        System.out.println(decoded);
    }
}
