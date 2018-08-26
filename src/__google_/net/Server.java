package __google_.net;

import __google_.crypt.Crypt;
import __google_.util.ByteUnzip;
import __google_.util.Byteable;
import __google_.util.Coder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Server extends Thread{
    private final Map<Byte, Executor> map = new HashMap<>();

    private final int port;
    private final Crypt crypt;
    private ServerSocket listn;
    private boolean close = false;

    public Server(int port, Crypt crypt){
        this.port = port;
        this.crypt = crypt;
        start();
    }

    public Server(int port){
        this(port, null);
    }

    @Override
    public void run(){
        try{
            listn = new ServerSocket(port);
            while (!close){
                Socket socket = listn.accept();
                if(socket != null){
                    try{
                        new Listn(socket);
                    }catch (IOException ex){
                        //Client not connected
                        ex.printStackTrace();
                    }
                }
            }
        }catch (IOException ex){
            if(!close)//Closed not with method close()
                ex.printStackTrace();
            //Server closed
        }
    }

    public void close(){
        close = true;
        try{
            listn.close();
        }catch (IOException ex){
            //Close listn port
        }
    }

    public void addListener(byte type, Executor executor){
        map.put(type, executor);
    }

    private class Listn extends CSSystem{
        public Listn(Socket socket) throws IOException{
            super(socket);
            start();
        }

        @Override
        public void run() {
            try{
                Response response = listn();
                if(response != null){
                    byte write[] = Byteable.toBytes(response);
                    if(crypt != null)write = crypt.encodeByte(write);
                    out.write(Coder.toBytes(write.length));
                    out.write(write);
                    out.flush();
                }
            }catch (IllegalArgumentException | SocketTimeoutException ex){
                //Error can be throw read or decrypt
            }catch (Throwable ex){
                ex.printStackTrace();
            }
            close();
        }

        private Response listn() throws IOException{
            byte byteSize[] = read(4);
            byte read[] = read(Coder.toInt(byteSize));
            if(crypt != null)read = crypt.decodeByte(read);
            Response response = Byteable.toObject(read, Response.class);
            Executor executor = map.get(response.getByteType());
            if(executor == null)return new Response((byte)-1, new byte[]{});
            return executor.apply(response);
        }
    }
}
