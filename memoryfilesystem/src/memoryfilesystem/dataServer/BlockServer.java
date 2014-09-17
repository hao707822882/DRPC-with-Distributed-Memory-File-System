package memoryfilesystem.dataServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import bean.CmdFlag;

public class BlockServer {

	Socket bs;
	Block nowBlock = null; // ָ��ǰ�洢���ݵ�Block
	DataOutputStream dout;
	DataInputStream din;
	ServerSocket sst;
	public static Map blockMap = new HashMap<String, Block>();

	public BlockServer() {

	}

	private void register(String address, int port)
			throws UnknownHostException, IOException {
		bs = new Socket(address, port);
		dout = new DataOutputStream(bs.getOutputStream());
		din = new DataInputStream(bs.getInputStream());
		dout.write(4);
	}

	private void beat() {
		new Thread(new Runnable() {// �����߳�
					boolean isrun = true;

					@Override
					public void run() {
						while (isrun) {
							try {
								System.out.println("blockServer�������߳�");
								dout.write(CmdFlag.dataServerbeat);
								Thread.sleep(1000);
							} catch (IOException | InterruptedException e) {
								isrun = false;
								e.printStackTrace();
							}
						}
					}
				}).start();
	}

	private void startFsSocket() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					sst = new ServerSocket(8888);
					while (true) {
						Socket st = sst.accept();
						WorkerThread wt = new WorkerThread(st);
						new Thread(wt).start();
					}

				} catch (IOException e) {
					System.out.println("�������ݴ���socketʧ��");
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void bootStart(String address, int port) {
		try {
			register(address, port);
			beat();
		} catch (IOException e) {
			System.out.println("���ݷ�����ע��ʧ��");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BlockServer bs = new BlockServer();
		bs.bootStart("127.0.0.1", 9999);
		bs.startFsSocket();
	}
}
