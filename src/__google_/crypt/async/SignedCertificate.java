package __google_.crypt.async;

import __google_.util.ByteUnzip;
import __google_.util.ByteZip;
import __google_.util.Byteable;
import __google_.util.Exceptions;

import java.util.Arrays;

public class SignedCertificate implements Byteable{
	private final byte signedHash[];
	private final Certificate certificate;

	public SignedCertificate(byte signedHash[], Certificate certificate){
		this.signedHash = signedHash;
		this.certificate = certificate;
	}

	public SignedCertificate(ByteUnzip unzip){
		signedHash = unzip.getBytes();
		certificate = new Certificate(unzip.getBytes());
	}

	public byte[] getSignedHash() {
		return signedHash;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public boolean checkCertificate(Certificate constant){
		try{
			byte hash[] = certificate.getHashPrivateKey();
			byte decoded[] = constant.decodeByte(signedHash);
			return Arrays.equals(hash, decoded);
		}catch (IllegalArgumentException ex){
			return false;
		}
	}

	public boolean checkSertificate(){
		return checkCertificate(Certificate.constant());
	}

	@Override
	public ByteZip toByteZip() {
		return new ByteZip().add(signedHash).add(certificate.privateKey());
	}

	public static SignedCertificate sign(Certificate constant, Certificate sign){
		return Exceptions.getThrowsEx(() -> new SignedCertificate(
				constant.encodeByte(sign.getHashPrivateKey()), constant));
	}
}
