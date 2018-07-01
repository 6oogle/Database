package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    private final String host;
    private final int port;

    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    private Socket socket = null;

    public boolean connect(){
        try{
            socket = new Socket(host, port);
            return true;
        }catch (IOException ex){
            return false;
        }
    }

    public boolean connected(){
        return socket != null && socket.isConnected();
    }

    public void send(String line){
        try{
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(line);
            writer.newLine();
            writer.flush();
        }catch (IOException ex){
            //Ignore
        }
    }

    public String read(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return reader.readLine();
        }catch (IOException ex){
            return null;
        }
    }

    public void close(){
        if(connected())
            try{
                socket.close();
            }catch (IOException ex){
                //Ignore
            }
    }
}
