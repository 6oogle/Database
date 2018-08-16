package __google_.crypt;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
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
		return publicKey().getEncoded();
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
}
