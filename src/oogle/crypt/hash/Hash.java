package oogle.crypt.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hash {
	public static final Hash
			MD5 = new Hash("MD5"),
			SHA_128 = new Hash("SHA-128"),
			SHA_256 = new Hash("SHA-256"),
			SHA_384 = new Hash("SHA-384"),
			SHA_512 = new Hash("SHA-512"),
			BCrypt = new BCrypt();

	private final String algorithm;

	public Hash(String algorithm) {
		this.algorithm = algorithm;
	}

	public byte[] encodeByte(byte array[], byte salt[]){
		try{
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(array);
			digest.update(salt);
			return digest.digest();
		}catch (NoSuchAlgorithmException ex){
			ex.printStackTrace();
			return null;
		}
	}

	public byte[] generateSalt(int rounds, SecureRandom random){
		byte array[] = new byte[rounds];
		random.nextBytes(array);
		return array;
	}

	public byte[] generateSalt(int rounds){
		return generateSalt(rounds, new SecureRandom(new byte[]{}));
	}

	public byte[] generateSalt(){
		return generateSalt(12);
	}

	public byte[] encodeByte(byte array[]) {
		return encodeByte(array, generateSalt());
	}
}
