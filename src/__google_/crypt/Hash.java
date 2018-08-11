package __google_.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum Hash {
	MD5,
	SHA_224,
	SHA_256,
	SHA_384,
	SHA_512;

	public byte[] hash(byte array[]) {
		try{
			return MessageDigest.getInstance(name().replace('_', '-')).digest(array);
		}catch (NoSuchAlgorithmException ex){
			throw new IllegalArgumentException("Illegal algorithm " + name());
		}
	}
}
