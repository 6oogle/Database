package oogle.sync;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

final class SocketChannel implements Channel {
    private final Socket socket;
    private final OutputStream out;

    SocketChannel(Socket socket) throws IOException{
        this.socket = socket;
        this.out = socket.getOutputStream();
    }

    @Override
    public void close() {
        try{
            socket.close();
        }catch (IOException ignore){}
    }

    @Override
    public void write(byte[] array, int offset, int size) {
        if(socket.isClosed())return;
        try{
            byte newArray[] = new byte[2 + size];
            newArray[0] = (byte)((short)size >> 8);
            newArray[1] = (byte)size;
            System.arraycopy(array, offset, newArray, 2, size);
            out.write(newArray);
            out.flush();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
