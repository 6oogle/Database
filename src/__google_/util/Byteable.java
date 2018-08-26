package __google_.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.Ref;

import static __google_.util.Reflect.create;
import static __google_.util.Reflect.getConstructor;

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

	static <T> T toObject(byte array[], Class<T> clazz){
		T obj = Reflect.create(Reflect.getConstructor(clazz));
		ByteUnzip unzip = new ByteUnzip(array);
		for(Field field : clazz.getDeclaredFields()){
			if(!Reflect.isEditable(field))continue;
			if(!field.isAccessible())field.setAccessible(true);
			Class klass = field.getType();
			byte write[] = unzip.getBytes();
			Object primitive = Coder.getPrimitiveObject(klass, write);
			if(primitive == null) Reflect.setToField(field, obj, toObject(write, klass));
			else Reflect.setToField(field, obj, primitive);
		}
		return obj;
	}

	static byte[] toBytes(Object invoke){
		Class clazz = invoke.getClass();
		ByteZip zip = new ByteZip();
		for(Field field : clazz.getDeclaredFields()){
			if(!Reflect.isEditable(field))continue;
			if(!field.isAccessible())field.setAccessible(true);
			Object obj = Reflect.getFromField(field, invoke);
			if(obj == null)obj = "lolkek";
			byte add[] = Reflect.getPrimitiveBytes(field, invoke);
			if(add == null)zip.add(toBytes(obj));
			else zip.add(add);
		}
		return zip.build();
	}

	static <T extends Byteable> T toByteable(byte array[], Class<T> clazz){
		Constructor<T> constructor = getConstructor(clazz, byte[].class);
		if(constructor == null)constructor = getConstructor(clazz, ByteUnzip.class);
		if(constructor == null)constructor = getConstructor(clazz, Packet.class);
		if(constructor == null)constructor = getConstructor(clazz, String.class);
		if(constructor == null) throw new IllegalArgumentException("No such constructor :c");
		return create(constructor, Coder.toString(array));
	}
}
