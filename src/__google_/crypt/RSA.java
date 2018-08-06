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

public class RSA extends AsyncCrypt{
    private final boolean privateKeyEncode;

    public RSA(int size, boolean privateKeyEncode){
        super("RSA");
        this.privateKeyEncode = privateKeyEncode;
        KeyPair pair = generate(size);
        publicKey = privateKeyEncode ? pair.getPrivate() : pair.getPublic();
        privateKey = privateKeyEncode ? pair.getPublic() : pair.getPrivate();
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
            if((privateKeyEncode ? privateKey : publicKey) != null)
                this.publicKey = RSAPublicKeyImpl.parse(new DerValue(privateKeyEncode ? privateKey : publicKey));
            if((privateKeyEncode ? publicKey : privateKey) != null)
                this.privateKey = RSAPrivateKeyImpl.parse(new DerValue(privateKeyEncode ? publicKey : privateKey));
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

    public boolean isPrivateKeyEncode() {
        return privateKeyEncode;
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
