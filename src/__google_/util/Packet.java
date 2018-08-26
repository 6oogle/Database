package __google_.util;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import static __google_.util.Reflect.getConstructor;

public abstract class Packet implements Byteable{
    private static final Map<String, Constructor<? extends Packet>> packets = new HashMap<>();
    private final String packetType;

    public Packet(){
        packetType = getType(getClass());
        if(packets.get(packetType) == null)register(getClass());
    }

    public String getType() {
        return packetType;
    }

    protected byte[] encodeBytes(){
        ByteZip zip = toByteZip();
        if(zip != null)return zip.build();
        return Coder.toBytes(toString());
    }

    public byte[] toBytes(){
        return new ByteZip().add(getType()).add(encodeBytes()).build();
    }

    public static Packet getPacket(byte array[]){
        ByteUnzip unzip = new ByteUnzip(array);
        String type = unzip.getString();
        Constructor<? extends Packet> constructor = packets.get(type);
        if(constructor == null)throw new IllegalArgumentException("Packet not registered");
        Object invoke = null;
        byte bytes[] = unzip.getBytes();
        Class arg = constructor.getParameterTypes()[0];
        if(arg == byte[].class) invoke = bytes;
        else if(arg == ByteUnzip.class) invoke = new ByteUnzip(bytes);
        else if(arg == String.class) invoke = Coder.toString(bytes);
        return Reflect.create(constructor, invoke);
    }

    public static void register(Class<? extends Packet> clazz) {
        Constructor<? extends Packet> constructor = getConstructor(clazz, ByteUnzip.class);
        if(constructor == null) constructor = getConstructor(clazz, byte[].class);
        if(constructor == null) constructor = getConstructor(clazz, String.class);
        if(constructor == null) throw new NullPointerException();
        packets.put(getType(clazz), constructor);
    }

    private static String getType(Class<? extends Packet> clazz) {
        try{
            return clazz.getDeclaredField("type").get(null).toString();
        }catch (NoSuchFieldException | IllegalAccessException ex){
            String name = clazz.getSimpleName();
            if(name.startsWith("Packet")) return name.substring(6);
            throw new IllegalArgumentException(ex);
        }
    }
}
