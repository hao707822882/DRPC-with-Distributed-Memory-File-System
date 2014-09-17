import intefacee.ClientAction;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class RpcClient implements ClientAction {

	Socket clientSocket = null;

	DataInputStream din;
	DataOutputStream dout;

	public RpcClient(String address, int port) throws UnknownHostException,
			IOException {
		this.clientSocket = new Socket(address, port);
		this.din = new DataInputStream(clientSocket.getInputStream());
		this.dout = new DataOutputStream(clientSocket.getOutputStream());
	}

	
	public void writeMessage(String message,int type) throws IOException {
		dout.writeByte((byte)type);
		dout.write((byte)message.getBytes().length);
		dout.write(message.getBytes());
	}

	@Override
	public String readMessage() throws IOException {
		byte[] data = null;
		int datalength = din.readByte();
		data = new byte[datalength];
		din.read(data, 0, datalength);
		System.out.println(datalength);
		String datastr = new String(data);
		System.out.println(datastr);
		data = null;
		return datastr;

	}


	@Override
	public void writeMessage(String message) throws IOException {
		
	}

	
	public static void main(String[] args) {
		try {
			RpcClient client=new RpcClient("127.0.0.1", 9999);
			client.writeMessage("testTask.task1",3);
		} catch (IOException e) {
		System.out.println("链接不上，服务器关闭了");
		e.printStackTrace();
		}
	}
}
