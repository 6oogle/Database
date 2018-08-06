package __google_.crypt;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class Blowfish extends SyncCrypt{
    public Blowfish(String key){
        super("Blowfish");
        this.key = new SecretKeySpec(key.getBytes(), getAlgorithm());
    }
}
