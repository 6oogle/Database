package __google_.crypt.async;

import __google_.util.Exceptions;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class RSA extends AsyncCrypt{
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
}
