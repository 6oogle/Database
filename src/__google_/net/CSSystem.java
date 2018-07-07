package __google_.net;

import __google_.crypt.Crypt;
import __google_.util.Listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class CSSystem extends Thread{
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final Crypt crypt;
    private final Listener listener;

    public CSSystem(Socket socket, Crypt crypt, NetListener listener) throws IOException{
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.crypt = crypt;
        this.listener = listener;
        setPriority(MIN_PRIORITY);
        listener.onConnected(this);
        start();
    }

    public void close(){
        if(socket == null)return;
        try{
            socket.close();
        }catch (IOException ex){}
    }

    @Override
    public void run(){
        try{
            while (true){
                String read = reader.readLine();
                if(read == null)break;//Socket disconnected
                if(crypt != null)read = crypt.decode(read);
                listener.read(read);
            }
        }catch (IOException ex){
            //Err socket
        }catch (IllegalArgumentException ex){
            //Err decrypt
        }
        close();
    }

    public void write(String str){
        if(crypt != null)str = crypt.encode(str);
        try{
            writer.write(str);
            writer.newLine();
            writer.flush();
        }catch (IOException ex){
            ex.printStackTrace();
            close();
        }
    }

    public boolean connected(){
        return socket != null && socket.isConnected();
    }
}
