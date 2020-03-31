package oogle.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	public static final Hash
			SHA_256 = new Hash("SHA-256"),
			SHA_384 = new Hash("SHA-384"),
			SHA_512 = new Hash("SHA-512");

	private final String algorithm;

	public Hash(String algorithm) {
		this.algorithm = algorithm;
	}

	public byte[] encode(byte[] array){
		try{
			return MessageDigest.getInstance(algorithm).digest(array);
		}catch (NoSuchAlgorithmException ex){
			throw new RuntimeException(ex);
		}
	}
}
