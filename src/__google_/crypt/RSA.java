package __google_.crypt;

import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.util.DerValue;

import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSA extends Crypt{

    private final boolean privateKeyEncode;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSA(int size, boolean privateKeyEncode){
        super("RSA");
        this.privateKeyEncode = privateKeyEncode;
        KeyPair pair = generate(size);
        publicKey = pair.getPublic();
        privateKey = pair.getPrivate();
    }

    public RSA(int size){
        this(size, true);
    }

    public RSA(){
        this(2048);
    }

    public RSA(String publicKey, String privateKey, boolean privateKeyEncode){
        super("RSA");
        this.privateKeyEncode = privateKeyEncode;
        try{
            if(publicKey != null)
                this.publicKey = RSAPublicKeyImpl.parse(new DerValue(publicKey));
            if(privateKey != null)
                this.privateKey = RSAPrivateKeyImpl.parse(new DerValue(privateKey));
        }catch (IOException ex){
            throw new IllegalArgumentException(ex);
        }
    }

    public RSA(String publicKey, boolean privateKeyEncode){
        this(publicKey, null, privateKeyEncode);
    }

    public RSA(String publicKey){
        this(publicKey, true);
    }

    public RSA(boolean privateKeyEncode, String privateKey){
        this(null, privateKey, privateKeyEncode);
    }

    @Override
    protected Key encodeKey() {
        return privateKeyEncode ? publicKey : privateKey;
    }

    @Override
    protected Key decodeKey() {
        return privateKeyEncode ? privateKey : publicKey;
    }

    private KeyPair generate(int size){
        try{
            KeyPairGenerator generator = KeyPairGenerator.getInstance(getAlgorithm());
            generator.initialize(size);
            return generator.genKeyPair();
        }catch (NoSuchAlgorithmException ex){
            //Unreal exception
            throw new Error(ex);
        }
    }
}
