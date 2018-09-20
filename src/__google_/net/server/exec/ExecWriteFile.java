package __google_.net.server.exec;

import __google_.io.FileIO;
import __google_.net.server.NetServer;
import __google_.util.ByteUnzip;

import java.io.IOException;

public class ExecWriteFile implements Exec{
	@Override
	public void accept(NetServer server) throws IOException {
		ByteUnzip unzip = new ByteUnzip(server.response().getContent());
		FileIO.writeBytes(unzip.getString(), unzip.getBytes());
	}
}
