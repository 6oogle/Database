package __google_;

import __google_.crypt.async.Certificate;
import __google_.crypt.async.SignedCertificate;
import __google_.io.FileIO;

public class Main{
    public static void main(String[] args){
        Certificate certificate = new Certificate(null, FileIO.readBytes("key.private"));
        Certificate sign = new Certificate(2048);
        SignedCertificate signed = SignedCertificate.sign(certificate, sign);
    }
}
