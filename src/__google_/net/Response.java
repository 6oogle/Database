package __google_.net;

public class Response {
	private byte byteType, content[];

	public Response(byte byteType, byte content[]){
		this.byteType = byteType;
		this.content = content;
	}

	public Response(){}

	public byte getByteType() {
		return byteType;
	}

	public byte[] getContent() {
		return content;
	}
}
