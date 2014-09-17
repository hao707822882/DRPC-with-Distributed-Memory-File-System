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
	Block nowBlock = null; // 指向当前存储数据的Block
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
		new Thread(new Runnable() {// 心跳线程
					boolean isrun = true;

					@Override
					public void run() {
						while (isrun) {
							try {
								System.out.println("blockServer的心跳线程");
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
					System.out.println("开启数据传输socket失败");
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
			System.out.println("数据服务器注册失败");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BlockServer bs = new BlockServer();
		bs.bootStart("127.0.0.1", 9999);
		bs.startFsSocket();
	}
}
