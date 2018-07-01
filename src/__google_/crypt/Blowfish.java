package crypt;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class Blowfish extends Crypt{

    private final SecretKeySpec key;

    public Blowfish(String key){
        super("Blowfish");
        this.key = new SecretKeySpec(key.getBytes(), getAlgorithm());
    }

    @Override
    protected Key encodeKey() {
        return key;
    }
}
