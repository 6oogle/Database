package __google_.packet;

import __google_.util.ByteUnzip;
import __google_.util.ByteZip;
import __google_.util.Coder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {

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
        ByteZip zip = encodeByteZip();
        if(zip != null)return zip.build();
        return Coder.toBytes(encodeString());
    }

    protected ByteZip encodeByteZip(){
        return null;
    }

    protected String encodeString(){
        return null;
    }

    public byte[] encode(){
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
        try{
            return constructor.newInstance(invoke);
        }catch (IllegalAccessException | InvocationTargetException | InstantiationException ex){
            throw new IllegalArgumentException(ex);
        }
    }

    public static void register(Class<? extends Packet> clazz) {
        Constructor<? extends Packet> constructor = getConstructor(clazz, ByteUnzip.class);
        if(constructor == null) constructor = getConstructor(clazz, byte[].class);
        if(constructor == null) constructor = getConstructor(clazz, String.class);
        if(constructor == null) throw new NullPointerException();
        packets.put(getType(clazz), constructor);
    }

    private static Constructor<? extends Packet> getConstructor(Class<? extends Packet> clazz, Class<?> arg){
        try{
            return clazz.getConstructor(arg);
        }catch (NoSuchMethodException ex){
            return null;
        }
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
