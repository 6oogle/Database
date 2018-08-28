package __google_.crypt;

import __google_.util.Exceptions;
import sun.security.rsa.RSAPrivateCrtKeyImpl;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public abstract class AsyncCrypt extends Crypt{
	protected Key publicKey, privateKey;

	protected AsyncCrypt(String algorithm) {
		super(algorithm);
	}

	public Key publicKey(){
		return publicKey;
	}

	public byte[] getBytePublicKey(){
		return publicKey().getEncoded();
	}

	public String getPublicKey(boolean usingBase64){
		return usingBase64 ? Base64.getEncoder().encodeToString(getBytePublicKey()) : new String(getBytePublicKey());
	}

	public String getPublicKey(){
		return getPublicKey(true);
	}

	public Key privateKey(){
		return privateKey;
	}

	public byte[] getBytePrivateKey(){
		return privateKey().getEncoded();
	}

	public String getPrivateKey(boolean usingBase64){
		return usingBase64 ? Base64.getEncoder().encodeToString(getBytePrivateKey()) : new String(getBytePrivateKey());
	}

	public String getPrivateKey(){
		return getPrivateKey(true);
	}

	@Override
	public byte[] encodeByte(byte array[]) {
		return cipher(array, Cipher.ENCRYPT_MODE, publicKey());
	}

	@Override
	public byte[] decodeByte(byte array[]) {
		return cipher(array, Cipher.DECRYPT_MODE, privateKey());
	}

	protected KeyPair generate(int size){
		return Exceptions.getThrowsEx(() -> {
				KeyPairGenerator generator = KeyPairGenerator.getInstance(getAlgorithm());
				generator.initialize(size);
				return generator.genKeyPair();
		});
	}

	protected Key decodePublic(byte publicKey[]){
		try{
			X509EncodedKeySpec key = new X509EncodedKeySpec(publicKey);
			KeyFactory factory = KeyFactory.getInstance(getAlgorithm());
			return factory.generatePublic(key);
		}catch (NoSuchAlgorithmException | InvalidKeySpecException ex){
			throw new IllegalArgumentException(ex);
		}
	}

	protected Key decodePrivate(byte privateKey[]){
		return Exceptions.getThrowsEx(() -> RSAPrivateCrtKeyImpl.newKey(privateKey), false);

	}
}
