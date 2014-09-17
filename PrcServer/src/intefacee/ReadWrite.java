package intefacee;

import java.io.IOException;

import server.Task;

public interface ReadWrite {

	public void sendMessage(String str) throws IOException;

	public  Task readMessage() throws IOException;

}
