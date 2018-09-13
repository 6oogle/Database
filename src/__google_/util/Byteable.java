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
		return Coder.toBytes(toString());
	}

	default ByteZip toByteZip(){
		return null;
	}

	static <T extends Byteable> T toByteable(byte array[], Class<T> clazz){
		Constructor<T> constructor = getConstructor(clazz, byte[].class);
		if(constructor != null)return create(constructor, array);
		constructor = getConstructor(clazz, ByteUnzip.class);
		if(constructor != null)return create(constructor, new ByteUnzip(array));
		constructor = getConstructor(clazz, String.class);
		if(constructor != null)return create(constructor, Coder.toString(array));
		throw new IllegalArgumentException("No such constructor :c");
	}
}
