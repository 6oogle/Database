package oogle.crypt.sync;

import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AES extends Crypt{
    public AES(int size, SecureRandom random){
        super("AES");
        byte array[] = new byte[size];
        random.nextBytes(array);
        this.key = new SecretKeySpec(array, getAlgorithm());
    }

    public AES(int size){
        this(size, new SecureRandom());
    }

    public AES(byte key[]){
        super("AES", key);
    }
}