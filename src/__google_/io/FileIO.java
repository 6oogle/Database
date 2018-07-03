package __google_.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;

public class FileIO {

    private static String prefix = System.getProperty("user.dir") + "/";

    public static void write(String strFile, String write){
        BufferedWriter writer = null;
        File file = getFile(strFile);
        try{
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.getParentFile().mkdir();
                file.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(write);
        }catch (IOException ex){}
        close(writer);
    }

    public static String read(String strFile){
        BufferedReader reader = null;
        File file = getFile(strFile);
        if(!file.exists())return null;
        StringBuffer buffer = new StringBuffer((int)file.length());
        try{
            reader = new BufferedReader(new FileReader(file));
            while (true){
                int i = reader.read();
                if(i == -1)break;
                buffer.append((char)i);
            }
        }catch (IOException ex){}
        close(reader);
        return buffer.toString();
    }

    public static void remove(String strFile){
        getFile(strFile).delete();
    }

    public static File[] getFiles(String strFile){
        File file = getFile(strFile);
        if(!file.exists() || file.isDirectory())return null;
        return file.listFiles();
    }

    private static File getFile(String file){
        return new File(prefix + file);
    }

    private static void close(Closeable closeable){
        if(closeable == null)return;
        try{
            if(closeable instanceof Flushable)
                ((Flushable) closeable).flush();
            closeable.close();
        }catch (IOException ex){
            //Close
        }
    }
}
