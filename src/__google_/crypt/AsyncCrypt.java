package __google_.crypt;

import sun.security.pkcs.PKCS8Key;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.security.auth.kerberos.KerberosTicket;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
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

	protected KeyPair generate(int size){
		try{
			KeyPairGenerator generator = KeyPairGenerator.getInstance(getAlgorithm());
			generator.initialize(size);
			return generator.genKeyPair();
		}catch (NoSuchAlgorithmException ex){
			//Unreal exception
			throw new Error(ex);
		}
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
		throw new UnsupportedOperationException("Sorry, I don't found decoder RSA private key");
	}
}
