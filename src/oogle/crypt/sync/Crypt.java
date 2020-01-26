package oogle.crypt.sync;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public abstract class Crypt {
    private final String algorithm;
    protected Key key;

    protected Crypt(String algorithm) {
        this.algorithm = algorithm;
    }

    protected Crypt(String algorithm, byte key[]){
        this.algorithm = algorithm;
        this.key = new SecretKeySpec(key, algorithm);
    }

    public byte[] getByteKey(){
        return key.getEncoded();
    }

    public byte[] encodeByte(byte array[]) {
        return cipher(array, Cipher.ENCRYPT_MODE, key);
    }

    public byte[] decodeByte(byte array[]) {
        return cipher(array, Cipher.DECRYPT_MODE, key);
    }

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
