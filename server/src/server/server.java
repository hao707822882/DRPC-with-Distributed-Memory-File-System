package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class server {
	ServerSocket sc;

	public static void main(String[] args) throws Throwable {

		ServerSocket sc = new ServerSocket(9999);
		List list = new ArrayList<>();
		while (true) {
			Socket cs = sc.accept();
			System.out.println("ִ��������");
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						DataInputStream di = new DataInputStream(
								cs.getInputStream());
						System.out.println("jinru  ��ȡ����");
						while (true) {

							int length = di.read();
							byte[] a = new byte[length];
							di.read(a, 0, length);
							System.out.println(new String(a));
						}
					} catch (IOException e) {
						System.out.println("�ͻ��˶Ͽ�����");
					}
				}
			}).start();
			;
		}

	}

}
