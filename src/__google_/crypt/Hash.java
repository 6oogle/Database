package __google_.crypt;

import __google_.util.Coder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum Hash {
	MD5,
	SHA_224,
	SHA_256,
	SHA_384,
	SHA_512;

	public byte[] byteHash(byte array[]) {
		try{
			return MessageDigest.getInstance(name().replace('_', '-')).digest(array);
		}catch (NoSuchAlgorithmException ex){
			throw new IllegalArgumentException("Illegal algorithm " + name());
		}
	}

	public byte[] byteHash(String line){
		return byteHash(Coder.toBytes(line));
	}

	public String hash(byte array[]) {
		return Coder.toHex(byteHash(array));
	}

	public String hash(String line){
		return hash(Coder.toBytes(line));
	}
}
