package intefacee;

import java.io.IOException;

public interface ClientAction {

	public void writeMessage(String message) throws IOException;
	
	public String readMessage()throws IOException;
}
