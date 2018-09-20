package __google_.net.client;

import __google_.net.NetWorker;
import __google_.net.Response;

import java.io.IOException;

public interface NetClient extends NetWorker {
	default Response apply() throws IOException{
		write();
		read();
		return response();
	}
}
