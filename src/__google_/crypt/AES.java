package __google_.crypt;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AES extends Crypt{

    private final SecretKeySpec key;

    public AES(String key){
        super("AES");
        this.key = new SecretKeySpec(key.getBytes(), getAlgorithm());
    }

    @Override
    protected Key encodeKey() {
        return key;
    }
}
