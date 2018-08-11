package __google_.net;

public enum ResponseType {
	HAPPY(0x00),
	UNKNOWN(0x01),
	FILE_WRITE(0x11),
	FILE_READ(0x12),
	FILE_LIST(0x13),
	NOT_FOUND(0x22),
	PERMISSION_DENIED(0x23),
	NOT_EXECUTORS(0x24);

	private final byte type;

	private ResponseType(int type){
		this.type = (byte)type;
	}

	public byte getType() {
		return type;
	}

	public static ResponseType typeOf(byte type){
		for(ResponseType search : values()){
			if(search == UNKNOWN) continue;
			else if(search.type == type) return search;
		}
		return UNKNOWN;
	}
}
