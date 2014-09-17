package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import bean.CmdFlag;

public class client {

	public static void main(String[] args) throws Throwable {
		Socket sc = new Socket("127.0.0.1", 8888);
		DataOutputStream dos = new DataOutputStream(sc.getOutputStream());
		DataInputStream din = new DataInputStream(sc.getInputStream());

		dos.write((byte) CmdFlag.downloadfile);
		dos.write("明的文件".getBytes().length);
		dos.write("明的文件".getBytes());
		din.readByte();
		int length = din.readByte();
		byte []data=new byte[length];
		din.read(data, 0, length);
		System.out.println(new String(data));
		/*
		 * dos.write(app.getBytes().length); dos.write(app.getBytes());
		 * 
		 */Thread.sleep(10000);

	}
}
