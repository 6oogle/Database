package __google_.net.server;

import __google_.net.NetWorker;
import __google_.util.Exceptions;

import java.io.IOException;

public interface NetServer extends NetWorker, Runnable{
	@Override
	default void run(){
		Exceptions.runThrowsEx(this::execute, false);
		closeOutException();
	}

	void execute() throws IOException;
}
