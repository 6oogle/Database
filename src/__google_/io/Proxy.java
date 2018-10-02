package __google_.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class Proxy extends Thread{
    private final Reader in;
    private final Writer out;

    public Proxy(Reader in, Writer out){
        this.in = in;
        this.out = out;
    }

    @Override
    public void run(){
        while (true){
            try{
                out.write(in.read());
            }catch (IOException ex){
                return;
            }
        }
    }
}
