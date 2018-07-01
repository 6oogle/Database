package __google_.crypt;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.Base64;

public abstract class Crypt {

    private final String algorithm;

    protected Crypt(String algorithm){
        this.algorithm = algorithm;
    }

    protected abstract Key encodeKey();

    protected Key decodeKey(){
        return encodeKey();
    }

    public String encKey(){
        return new String(encodeKey().getEncoded());
    }

    public String decKey(){
        return new String(decodeKey().getEncoded());
    }

    public String encode(String line){
        try{
            Cipher cipher = Cipher.getInstance(getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, encodeKey());
            return Base64.getEncoder().encodeToString(cipher.doFinal(line.getBytes()));
        }catch (Exception ex){
            throw new IllegalArgumentException(ex);
        }
    }

    public String decode(String line){
        try{
            Cipher cipher = Cipher.getInstance(getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, decodeKey());
            return new String(cipher.doFinal(Base64.getDecoder().decode(line)));
        }catch (Exception ex){
            throw new IllegalArgumentException(ex);
        }
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
