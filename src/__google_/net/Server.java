package __google_.net;

import __google_.crypt.Crypt;
import __google_.crypt.async.SignedRSA;
import __google_.net.exec.Exec;
import __google_.net.exec.ExecRSA;
import __google_.util.Coder;
import __google_.util.Exceptions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread{
    private final Map<Byte, Exec> map = new HashMap<>();

    private final int port;
    private Crypt crypt;
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
                if(socket == null) continue;
                try{
                    new Listn(socket, crypt);
                }catch (IOException ex){
                    //Client not connected
                    ex.printStackTrace();
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
        Exceptions.runThrowsEx(() -> listn.close());
    }

    public void addListener(byte type, Exec exec){
        map.put(type, exec);
    }

    public void addListener(int type, Exec exec){
        addListener((byte)type, exec);
    }

    public void setCertificate(SignedRSA certificate){
        addListener(0, new ExecRSA(certificate));
        this.crypt = certificate.getRSA();
    }

    private class Listn extends CSSystem{
        public Listn(Socket socket, Crypt crypt) throws IOException{
            super(socket, crypt);
            start();
        }

        @Override
        public void run() {
            try{
                this.response = exec();
                write();
            }catch (IllegalArgumentException | SocketTimeoutException ex){
                //Error can be throw read or decrypt
            }catch (Exception ex){
                ex.printStackTrace();
            }
            close();
        }

        private Response exec() throws IOException {
            Response response = read();
            if(response == null) return nullResponce();
            Exec exec = map.get(response.getType());
            if(exec == null) return nullResponce();
            response = exec.apply(response);
            if(response == null)return nullResponce();
            return response;
        }

        private Response nullResponce(){
            return new Response(127);
        }
    }
}
