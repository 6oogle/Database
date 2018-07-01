package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProxyStream extends Thread{
    private final InputStream in;
    private final OutputStream out;

    public ProxyStream(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
    }

    @Override
    public void run(){
        try{
            out.write(in.read());
        }catch (IOException ex){}
    }
}
