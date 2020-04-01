package oogle.sync;

import oogle.util.byteable.FastEncoder;

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
            FastEncoder encoder = new FastEncoder(2 + size);
            encoder.writeShort((short)size);
            encoder.writeBytes(array, offset, size);
            out.write(encoder.generate());
            out.flush();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
