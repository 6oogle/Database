package __google_.crypt.sync;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AES extends SyncCrypt{
    public AES(String key){
        super("AES");
        this.key = new SecretKeySpec(key.getBytes(), getAlgorithm());
    }

    @Override
    public Key key() {
        return key;
    }
}
