package __google_.crypt;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.Base64;

public abstract class Crypt {
    private final String algorithm;

    protected Crypt(String algorithm){
        this.algorithm = algorithm;
    }

    public abstract byte[] encodeByte(byte array[]);

    public abstract byte[] decodeByte(byte array[]);

    public String getAlgorithm() {
        return algorithm;
    }

    protected byte[] cipher(byte array[], int mode, Key key){
        try{
            Cipher cipher = Cipher.getInstance(getAlgorithm());
            cipher.init(mode, key);
            return cipher.doFinal(array);
        }catch (Exception ex){
            throw new IllegalArgumentException(ex);
        }
    }
}
