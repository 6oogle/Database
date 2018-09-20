package __google_.net;

import __google_.util.Byteable;

public class Response implements Byteable{
	private byte type, content[];

	public Response(byte type, byte content[]){
		this.type = type;
		this.content = content == null ? new byte[]{} : content;
	}

	public Response(int type, byte content[]){
		this((byte)type, content);
	}

	public Response(int type){
		this(type, new byte[]{});
	}

	public Response(){
	}

	public byte getType() {
		return type;
	}

	public byte[] getContent() {
		return content;
	}
}
