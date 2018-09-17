package __google_.crypt.async;

import __google_.crypt.hash.BCrypt;
import __google_.crypt.hash.SHA_512;
import __google_.util.Exceptions;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class RSA extends AsyncCrypt{
    private boolean certificate = false;

    public RSA(SecureRandom random, int keySize){
        super("RSA");
        Exceptions.runThrowsEx(() -> {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(getAlgorithm());
            generator.initialize(keySize, random);
            KeyPair keyPair = generator.genKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        }, false);
    }

    public RSA(SecureRandom random){
        this(random, 2048);
    }

    public RSA(int size){
        super("RSA");
        KeyPair pair = generate(size);
        publicKey = pair.getPublic();
        privateKey = pair.getPrivate();
    }

    public RSA(){
        this(2048);
    }

    public RSA(byte publicKey[], byte privateKey[]){
        super("RSA");
        if(publicKey != null)this.publicKey = decodePublic(publicKey);
        if(privateKey != null)this.privateKey = decodePrivate(privateKey);
    }

    public RSA(byte publicKey[]){
        this(publicKey, null);
    }

    @Override
    public byte[] encodeByte(byte[] array) {
        return cipher(array, Cipher.ENCRYPT_MODE, certificate ? privateKey() : publicKey());
    }

    @Override
    public byte[] decodeByte(byte[] array) {
        return cipher(array, Cipher.DECRYPT_MODE, certificate ? publicKey() : privateKey());
    }

    public void setCertificate(boolean certificate){
        this.certificate = certificate;
    }

    public boolean isCertificate(){
        return certificate;
    }

    public static RSA generate(String password, int keySize, int rounds){
        BCrypt hash = new BCrypt();
        SHA_512 sha = new SHA_512();
        SecureRandom random = new SecureRandom(sha.encodeByte(password));
        byte salt[] = hash.generateSalt(rounds, random);
        return new RSA(new SecureRandom(hash.encodeByte(password, salt)), keySize);
    }

    private static RSA constant = null;

    static{
        try{
            InputStream in = RSA.class.getClassLoader().getResourceAsStream("__google_/crypt/async/key.public");
            byte key[] = new byte[in.available()];
            in.read(key);
            in.close();
            constant = new RSA(key);
        }catch (IOException ex){
            System.err.println("No found constant certificate");
        }
    }

    public static RSA constant(){
        return constant;
    }
}
