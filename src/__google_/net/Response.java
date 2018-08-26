package __google_.net;

import __google_.util.ByteUnzip;
import __google_.util.ByteZip;

public class Response {
	private final byte byteType;

	private final byte content[];

	public Response(byte byteType, byte content[]){
		this.byteType = byteType;
		this.content = content;
	}

	public Response(ByteUnzip unzip){
		byteType = unzip.getByte();
		content = unzip.getBytes();
	}

	public byte getByteType() {
		return byteType;
	}

	public byte[] getContent() {
		return content;
	}

	public byte[] toBytes(){
		return new ByteZip().add(byteType).add(content).build();
	}
}
