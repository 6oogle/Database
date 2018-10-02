package __google_.crypt.async;

import __google_.util.ByteUnzip;
import __google_.util.ByteZip;
import __google_.util.Byteable;
import __google_.util.Exceptions;

import java.util.Arrays;

public class SignedRSA implements Byteable{
	private final byte signedHash[];
	private final RSA rsa;

	public SignedRSA(byte signedHash[], RSA rsa){
		this.signedHash = signedHash;
		this.rsa = rsa;
	}

	public SignedRSA(ByteUnzip unzip){
		byte array[] = unzip.getBytes();
		signedHash = array.length == 0 ? null : array;
		rsa = new RSA(unzip.getBytes());
	}

	public byte[] getSignedHash() {
		return signedHash;
	}

	public RSA getRSA() {
		return rsa;
	}

	public boolean checkCertificate(RSA rsa){
		if(signedHash == null)return false;
		boolean last = rsa.isCertificate();
		rsa.setCertificate(true);
		try{
			byte hash[] = this.rsa.getHashPrivateKey();
			byte decoded[] = rsa.decodeByte(signedHash);
			return Arrays.equals(hash, decoded);
		}catch (IllegalArgumentException ex){
			return false;
		}finally{
			rsa.setCertificate(last);
		}
	}

	public boolean checkCertificate(){
		return checkCertificate(RSA.constant());
	}

	@Override
	public ByteZip toByteZip() {
		return new ByteZip().add(signedHash == null ? new byte[]{} :
				signedHash).add(rsa.getBytePublicKey());
	}

	public static SignedRSA sign(RSA constant, RSA sign){
		return Exceptions.getThrowsEx(() -> new SignedRSA(
				constant.encodeByte(sign.getHashPublicKey()),
				new RSA(sign.getBytePublicKey())), false);
	}
}
