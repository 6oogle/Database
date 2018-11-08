package __google_.util;

import java.io.*;
import java.util.List;

public class FileIO {
    //Can be set something, for libraries
    public static String prefix = "AppData/";

    public static void writeByteable(String strFile, Byteable byteable){
        writeByteable(getFile(strFile), byteable);
    }

    public static void writeByteable(File file, Byteable byteable){
        writeBytes(file, byteable.toBytes());
    }

    public static void writeBytes(String strFile, byte array[]){
        writeBytes(getFile(strFile), array);
    }

    public static void writeBytes(File file, byte array[]){
        if(!file.exists())create(file);
        close(Exceptions.getThrowsEx(() -> {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(array);
            return out;
        }));
    }

    public static void writeList(String strFile, List<String> list){
        writeList(getFile(strFile), list);
    }

    public static void writeList(File file, List<String> list){
        write(file, Coder.toString(list));
    }

    public static void write(String strFile, String write){
        write(getFile(strFile), write);
    }

    public static void write(File file, String write){
        if(!file.exists())create(file);
        close(Exceptions.getThrowsEx(() -> {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(write);
            return out;
        }));
    }

    public static <T extends Byteable> T readByteable(String strFile, Class<T> clazz){
        return readByteable(getFile(strFile), clazz);
    }

    public static <T extends Byteable> T readByteable(File file, Class<T> clazz){
        return Byteable.toByteable(readBytes(file), clazz);
    }

    public static byte[] readBytes(String strFile){
        return readBytes(getFile(strFile));
    }

    public static byte[] readBytes(File file){
        if(!file.exists())return null;
        byte array[] = new byte[(int)file.length()];
        close(Exceptions.getThrowsEx(() -> {
            InputStream stream = new BufferedInputStream(new FileInputStream(file));
            stream.read(array);
            return stream;
        }));
        return array;
    }

    public static List<String> readList(String strFile){
        return readList(getFile(strFile));
    }

    public static List<String> readList(File file){
        return Coder.toList(read(file));
    }

    public static String read(String strFile){
        return read(getFile(strFile));
    }

    public static String read(File file){
        if(!file.exists())return null;
        StringBuilder buffer = new StringBuilder((int)file.length());
        close(Exceptions.getThrowsEx(() -> {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (true){
                int i = reader.read();
                if(i == -1)break;
                buffer.append((char)i);
            }
            return reader;
        }));
        return buffer.toString();
    }

    public static void create(String strFile){
        create(getFile(strFile));
    }

    public static void create(File file){
        Exceptions.runThrowsEx(() -> {
            file.getParentFile().mkdirs();
            file.getParentFile().mkdir();
            file.createNewFile();
        });
    }

    public static void remove(String strFile){
        getFile(strFile).delete();
    }

    public static File[] getFiles(String strFile){
        File file = getFile(strFile);
        if(!file.exists() || file.isDirectory())return null;
        return file.listFiles();
    }

    public static File getFile(String file){
        return file.startsWith("/") ? new File(file) : new File(System.getProperty("user.dir") + '/' + prefix + file);
    }

    private static void close(InputStream in){
        if(in == null)return;
        Exceptions.runThrowsEx(in::close);
    }

    private static void close(Reader in){
        if(in == null)return;
        Exceptions.runThrowsEx(in::close);
    }

    private static void close(OutputStream out){
        if(out == null)return;
        Exceptions.runThrowsEx(() -> {
            out.flush();
            out.close();
        });
    }

    private static void close(Writer out){
        if(out == null)return;
        Exceptions.runThrowsEx(() -> {
            out.flush();
            out.close();
        });
    }
}
