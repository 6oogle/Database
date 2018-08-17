package __google_.crypt;

import java.security.KeyPair;

public class RSA extends AsyncCrypt{
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
