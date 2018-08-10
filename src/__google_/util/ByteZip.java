package __google_.util;

public class ByteZip {
	private final NodeList<byte[]> list = new NodeList<>();

	public byte[] build(){
		byte result[] = new byte[size()];
		int write = 0;
		for(byte array[] : list){
			byte size[] = Coder.getSize(array.length);
			result = Coder.addBytes(size, write, result);
			write = write + size.length;
			result = Coder.addBytes(array, write, result);
			write = write + array.length;
		}
		return result;
	}

	public ByteZip add(String object){
		return add(Coder.toBytes(object));
	}

	public ByteZip add(boolean object){
		return add(Coder.toBytes(object));
	}

	public ByteZip add(long object){
		return add(Coder.toBytes(object));
	}

	public ByteZip add(int object){
		return add(Coder.toBytes(object));
	}

	public ByteZip add(short object){
		return add(Coder.toBytes(object));
	}

	public ByteZip add(byte object){
		return add(Coder.toBytes(object));
	}

	public ByteZip add(byte array[]){
		list.add(array);
		return this;
	}

	public int size(){
		int size = list.size();
		for(int i = 0; i < list.size(); i++){
			byte array[] = list.get(i);
			size = size + array.length;
			if(array.length > 126)size = size + 4;
			//Add integer size
		}
		return size;
	}
}
