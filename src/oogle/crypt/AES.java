package oogle.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

public class AES {
    private static final String algorithm = "AES";
    protected Key key;

    public AES(int size, SecureRandom random){
        byte array[] = new byte[size];
        random.nextBytes(array);
        this.key = new SecretKeySpec(array, getAlgorithm());
    }

    public AES(int size){
        this(size, new SecureRandom());
    }

    public AES(byte key[]){
        this.key = new SecretKeySpec(key, algorithm);
    }

    public byte[] getKey(){
        return key.getEncoded();
    }

    public byte[] encode(byte array[]) {
        return cipher(array, Cipher.ENCRYPT_MODE, key);
    }

    public byte[] decode(byte array[]) {
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
