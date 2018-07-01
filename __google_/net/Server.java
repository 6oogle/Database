package __google_.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private final int port;
    private final Listener listener;
    private boolean close = false;

    public Server(int port, Listener listener){
        this.port = port;
        this.listener = listener;
    }
    @Override
    public void run(){
        try{
            ServerSocket listn = new ServerSocket(port);
            while (!close){
                Socket socket = listn.accept();
                if(socket != null)new ThrListener(socket).start();
            }
        }catch (IOException ex){
            //Error, server closed, but not close()
            ex.printStackTrace();
        }
    }

    public void close(){
        close = true;
    }

    public interface Listener{
        //run async
        void run(ThrListener listener) throws IOException;
    }

    public class ThrListener extends Thread{

        private final Socket socket;
        private BufferedReader reader;
        private BufferedWriter writer;

        public ThrListener(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){
            try{
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                listener.run(this);
            }catch (IOException ex){
                //Error!
                ex.printStackTrace();
            }
        }

        public void write(String line) throws IOException{
            writer.write(line);
            writer.newLine();
            writer.flush();
        }

        public String read() throws IOException{
            return reader.readLine();
        }

        public void close() throws IOException{
            socket.close();
        }

        public boolean connected(){
            return socket != null && socket.isConnected();
        }
    }
}
