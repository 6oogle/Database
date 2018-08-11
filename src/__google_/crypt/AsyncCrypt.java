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

	public byte[] getPublicKey(){
		return publicKey.getEncoded();
	}

	public byte[] getPrivateKey(){
		return publicKey.getEncoded();
	}

	@Override
	public byte[] encodeByte(byte array[]) {
		return cipher(array, Cipher.ENCRYPT_MODE, publicKey);
	}

	@Override
	public byte[] decodeByte(byte array[]) {
		return cipher(array, Cipher.DECRYPT_MODE, privateKey);
	}
}
