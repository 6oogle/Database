package __google_.crypt.sync;

import javax.crypto.spec.SecretKeySpec;

public class Blowfish extends SyncCrypt{
    public Blowfish(String key){
        super("Blowfish");
        this.key = new SecretKeySpec(key.getBytes(), getAlgorithm());
    }
}
