package __google_.crypt.async;

import __google_.util.Exceptions;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class Certificate extends AsyncCrypt{
	public Certificate(SecureRandom random, int keySize){
		super("RSA");
		Exceptions.runThrowsEx(() -> {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(getAlgorithm());
			generator.initialize(keySize, random);
			KeyPair keyPair = generator.genKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
		}, false);
	}

	public Certificate(SecureRandom random){
		this(random, 2048);
	}

	public Certificate(int size){
		super("RSA");
		KeyPair pair = generate(size);
		publicKey = pair.getPublic();
		privateKey = pair.getPrivate();
	}

	public Certificate(){
		this(2048);
	}

	public Certificate(byte privateKey[], byte publicKey[]){
		super("RSA");
		if(publicKey != null)this.publicKey = decodePublic(publicKey);
		if(privateKey != null)this.privateKey = decodePrivate(privateKey);
	}

	public Certificate(byte publicKey[]){
		this(null, publicKey);
	}

	@Override
	public Key publicKey() {
		return privateKey;
	}

	@Override
	public Key privateKey() {
		return publicKey;
	}

	private static Certificate constant = null;

	static{
		try{
			InputStream in = Certificate.class.getClassLoader().getResourceAsStream("__google_/crypt/async/key.public");
			byte key[] = new byte[in.available()];
			in.read(key);
			in.close();
			constant = new Certificate(key);
		}catch (IOException ex){
			System.err.println("No found constant certificate");
		}
	}

	public static Certificate constant(){
		return constant;
	}
}
