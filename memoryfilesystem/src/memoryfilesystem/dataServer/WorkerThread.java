package memoryfilesystem.dataServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import bean.CmdFlag;
import utilclass.ForEach;

/***
 * 
 * ������������ ���пͻ��Ͽ�����ʱ����server�е�workmap��ɾ���ÿͻ��� 1������ 2ע���� 3������
 * 
 * **/

public class WorkerThread implements Runnable {
	Socket csocket = null;
	DataInputStream di = null;
	DataOutputStream dos = null;

	public WorkerThread(Socket cs) {
		this.csocket = cs;
		try {
			di = new DataInputStream(csocket.getInputStream());
			dos = new DataOutputStream(csocket.getOutputStream());
		} catch (IOException e) {
			try {
				cs.close();
			} catch (IOException e1) {
				System.out.println("���Ѿ��ر�");
				e1.printStackTrace();
			}
			System.out.println("��ȡsocket��������ʧ��");
			e.printStackTrace();
		}
		System.out.println("�����߳�����");
	}

	/***
	 * 
	 * ������ݵ��xȡ�c̎��
	 * 
	 * ***/
	@Override
	public void run() {
		boolean Isrun = true;
		while (Isrun) {
			try {
				readMessage();
			} catch (Exception e) {
				Isrun = false;
				try {
					csocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

	public void readMessage() throws IOException {

		byte[] data = null;
		int datatype = di.readByte();
		System.out.println("datatype" + datatype);
		if (datatype == CmdFlag.downloadfile) {
			System.out.println("��������������");
			String filename = getDataString();
			Block block = (Block) BlockServer.blockMap.get(filename);
			byte[] tempdata = block.readData();
			System.out.println(new String(tempdata));
			dos.write(CmdFlag.serializabledata);
			dos.write(tempdata.length);
			dos.write(tempdata);
		} else if (datatype == CmdFlag.upload) {
			Data da = getData();
			Block block = new Block(da.getData());
			BlockServer.blockMap.put(da.getName(), block);
			System.out.println("�ϴ��ɹ�");

		}
	}

	private String getDataString() {
		byte[] data = null;
		String path = "";
		int length;
		try {
			length = di.readByte();
			data = new byte[length];
			di.read(data, 0, length);
			path = new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	private Data getData() {
		byte[] data = null;
		Data da = null;
		int length;
		try {
			length = di.readByte();
			data = new byte[length];
			di.read(data, 0, length);
			String filename = new String(data);
			int datalength = di.readByte();
			data = new byte[datalength];
			di.read(data, 0, datalength);
			System.out.println("�ϴ���������" + new String(data));
			da = new Data(data, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return da;
	}

	// ////////////////////////////////

	public void addData(String blockname, byte[] data) {
		try {
			Block nowBlock = new Block(data);
			BlockServer.blockMap.put(blockname, nowBlock);
			nowBlock.writeData(data);
		} catch (Exception e) {
		}
	}

	public byte[] readData(String name) {
		Block block = (Block) BlockServer.blockMap.get(name);
		return block.readData();
	}
}
