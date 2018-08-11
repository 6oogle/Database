package __google_.io;

import __google_.util.Global;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class FileIO {
    //Can be set something, for libraries
    public static String prefix = "";

    public static void writeBytes(File file, byte array[]){
        BufferedOutputStream out = null;
        if(!file.exists())create(file);
        try{
            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(array);
        }catch (IOException ex){}
        close(out);
    }

    public static byte[] readBytes(File file){
        if(!file.exists())return null;
        InputStream in = null;
        byte array[] = new byte[(int)file.length()];
        try{
            in = new BufferedInputStream(new FileInputStream(file));
            in.read(array);
        }catch (IOException ex){}
        close(in);
        return array;
    }

    public static void write(File file, String write){
        BufferedWriter out = null;
        if(!file.exists())create(file);
        try{
            out = new BufferedWriter(new FileWriter(file));
            out.write(write);
            out.flush();
        }catch (IOException ex){}
        close(out);
    }

    public static String read(File file){
        BufferedReader reader = null;
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

    public static void create(File file){
        try{
            file.getParentFile().mkdirs();
            file.getParentFile().mkdir();
            file.createNewFile();
        }catch (IOException ex){
            if(Global.debug)ex.printStackTrace();
        }
    }

    public static File getFile(String file){
        return new File(prefix + file);
    }

    private static void close(InputStream in){
        if(in == null)return;
        try{
            in.close();
        }catch (IOException ex){
            //Close
        }
    }

    private static void close(Reader in){
        if(in == null)return;
        try{
            in.close();
        }catch (IOException ex){
            //Close
        }
    }

    private static void close(OutputStream out){
        if(out == null)return;
        try{
            out.flush();
            out.close();
        }catch (IOException ex){
            //Close
        }
    }

    private static void close(Writer out){
        if(out == null)return;
        try{
            out.flush();
            out.close();
        }catch (IOException ex){
            //Close
        }
    }
}
