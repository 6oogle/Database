package oogle.util;

import java.io.*;

public class IOUtil {
    public static IOException write(String strFile, String write){
        return write(getFile(strFile), write);
    }

    public static IOException write(File file, String write){
        return writeBytes(file, write.getBytes());
    }

    public static IOException writeBytes(String strFile, byte array[]){
        return writeBytes(getFile(strFile), array);
    }

    public static IOException writeBytes(File file, byte array[]){
        if(!file.exists())create(file);
        try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(array);
            return null;
        }catch (IOException ex){
            return ex;
        }
    }

    public static String read(String strFile){
        return read(getFile(strFile));
    }

    public static String read(File file){
        byte array[] = readBytes(file);
        return array == null ? null : new String(array);
    }

    public static byte[] readBytes(String strFile){
        return readBytes(getFile(strFile));
    }

    public static byte[] readBytes(File file){
        if(!file.exists())return null;
        byte array[] = new byte[(int)file.length()];
        try(InputStream stream = new BufferedInputStream(new FileInputStream(file))){
            IOUtil.readFromStream(array, stream);
            return array;
        }catch (IOException ex){
            return null;
        }
    }

    public static boolean create(String strFile){
        return create(getFile(strFile));
    }

    public static boolean create(File file){
        try{
            file.getParentFile().mkdirs();
            file.getParentFile().mkdir();
            file.createNewFile();
            return true;
        }catch (IOException ex){
            return false;
        }
    }

    public static boolean remove(String strFile){
        return remove(getFile(strFile));
    }

    public static boolean remove(File file){
        return file.delete();
    }

    public static File[] getFiles(String strFile){
        File file = getFile(strFile);
        if(!file.exists() || file.isDirectory())return null;
        return file.listFiles();
    }

    public static File getFile(String file){
        return new File(file);
    }

    public static byte[] readFromStream(int size, InputStream in) throws IOException {
        byte array[] = new byte[size];
        readFromStream(array, in);
        return array;
    }

    public static void readFromStream(byte array[], InputStream in) throws IOException {
        int i = 0, size = array.length;
        while (i != size)
            i = i + in.read(array, i, size - i);
    }
}
