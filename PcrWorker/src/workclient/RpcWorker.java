package workclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import bean.Task;

public class RpcWorker {

	Socket wsc = null;
	DataOutputStream dout = null;
	DataInputStream din = null;

	public RpcWorker(String address, int port) throws UnknownHostException,
			IOException {
		this.wsc = new Socket(address, port);
		this.dout = new DataOutputStream(wsc.getOutputStream());
		this.din = new DataInputStream(wsc.getInputStream());
		rigisterWorker();
	}

	private void rigisterWorker() throws IOException {
		dout.write((byte) 2);
	}

	private void listenToWork() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						System.out.println("��������");
						Task task = readMessage(din);
						if (task != null) {
							new TaskRunner(task).dotask();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	

	/***
	 * 
	 * ��ȡ��Ϣ
	 * 
	 * ***/
	public static Task readMessage(DataInputStream di) throws IOException {
		Task task = null;
		byte[] data = null;
		int datatype = di.readByte();
		if (datatype == 3) {
			int datalength = di.readByte();
			data = new byte[datalength];
			di.read(data, 0, datalength);
			System.out.println(datalength);
			String datastr = new String(data);
			data = null;
			task = new Task(datastr);
		}
		return task;
	}

	public static void main(String[] args) {
		try {
			final RpcWorker rpcWorker = new RpcWorker("127.0.0.1", 9999);
			new Thread(new Runnable() {// �����߳�
						@Override
						public void run() {
							while (true) {
								try {
									System.out.println("�����߳��ڹ���");
									Thread.sleep(1000);
									rpcWorker.dout.write((byte) 1);
								} catch (IOException | InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}).start();
			rpcWorker.listenToWork();// ��������߳�
		} catch (IOException e) {
			System.out.println("��ʼ�������ڵ�ʧ��");
			e.printStackTrace();
		}
	}
}
