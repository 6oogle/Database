package __google_.net.server.exec;

import __google_.crypt.async.RSA;
import __google_.crypt.sync.AES;
import __google_.net.server.NetServer;

public class ExecAES implements Exec{
    @Override
    public void accept(NetServer server) {
        if(!(server.crypt() instanceof RSA))return;
        server.setCrypt(new AES(server.crypt().decodeByte(server.response().getContent())));
    }
}
