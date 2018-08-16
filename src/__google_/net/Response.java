package __google_.net;

import __google_.util.ByteUnzip;
import __google_.util.ByteZip;

public class Response {
	private final ResponseType type;

	private final byte byteType;

	private final byte content[];

	private Response(ResponseType type, byte byteType, byte content[]){
		this.type = type;
		this.byteType = byteType;
		this.content = content;
	}

	public Response(ResponseType type, byte content[]){
		this(type, type.getType(), content);
	}

	public Response(byte byteType, byte content[]){
		this(ResponseType.typeOf(byteType), byteType, content);
	}

	public Response(ResponseType type){
		this(type, new byte[]{});
	}

	public Response(byte array[]){
		ByteUnzip unzip = new ByteUnzip(array);
		byteType = unzip.getByte();
		type = ResponseType.typeOf(byteType);
		content = unzip.getBytes();
	}

	public byte getByteType() {
		return byteType;
	}

	public ResponseType getType() {
		return type;
	}

	public byte[] getContent() {
		return content;
	}

	public byte[] toBytes(){
		return new ByteZip().add(byteType).add(content).build();
	}
}
