package __google_.net;

import __google_.crypt.Crypt;
import __google_.util.ByteUnzip;
import __google_.util.Coder;
import __google_.util.Fabric;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Server extends Thread{
    private static Map<Short, Fabric<Function<byte[], byte[]>>> map = new HashMap<>();

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
            //Error, server closed, but not close()
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

    public static void addListener(short type, Fabric<Function<byte[], byte[]>> listener){
        map.put(type, listener);
    }

    private class Listn extends CSSystem{
        public Listn(Socket socket) throws IOException{
            super(socket);
            start();
        }

        @Override
        public void run() {
            try{
                listn();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            close();
        }

        private void listn() throws IOException{
            byte byteSize[] = read(4);
            byte read[] = read(Coder.toInt(byteSize));
            if(crypt != null)read = crypt.decodeByte(read);
            ByteUnzip unzip = new ByteUnzip(read);
            byte write[] = map.get(unzip.getShort()).newObject().apply(unzip.getBytes());
            if(crypt != null)write = crypt.encodeByte(write);
            out.write(Coder.toBytes(write.length));
            out.write(write);
            out.flush();
            socket.close();
        }
    }
}
