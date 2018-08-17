package __google_.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public interface Byteable {
	default byte[] toBytes(){
		ByteZip zip = toByteZip();
		if(zip != null)return zip.build();
		Packet packet = toPacket();
		if(packet != null)return packet.toBytes();
		return Coder.toBytes(toString());
	}

	default Packet toPacket(){
		return null;
	}

	default ByteZip toByteZip(){
		return null;
	}

	static <T extends Byteable> T toByteable(byte array[], Class<T> clazz){
		Constructor<T> constructor = getConstructor(clazz, byte[].class);
		if(constructor == null)constructor = getConstructor(clazz, ByteUnzip.class);
		else return create(constructor, array);
		if(constructor == null)constructor = getConstructor(clazz, Packet.class);
		else return create(constructor, new ByteUnzip(array));
		if(constructor == null)constructor = getConstructor(clazz, String.class);
		else return create(constructor, Packet.getPacket(array));
		if(constructor == null) throw new IllegalArgumentException("No such constructor :c");
		else return create(constructor, Coder.toString(array));
	}

	static <T extends Byteable> Constructor<T> getConstructor(Class<T> clazz, Class<?> arg){
		try{
			return clazz.getConstructor(arg);
		}catch (NoSuchMethodException ex){
			return null;
		}
	}

	static <T extends Byteable> T create(Constructor<T> constructor, Object arg){
		try{
			return constructor.newInstance(arg);
		}catch (InvocationTargetException | IllegalAccessException | InstantiationException ex){
			throw new IllegalArgumentException(ex);
		}
	}
}
