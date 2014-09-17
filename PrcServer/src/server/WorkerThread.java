package server;

import intefacee.ReadWrite;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import utilclass.ForEach;
import bean.CmdFlag;

/***
 * 
 * ������������ ���пͻ��Ͽ�����ʱ����server�е�workmap��ɾ���ÿͻ��� 1������ 2ע���� 3������
 * 
 * **/

public class WorkerThread implements Runnable, ReadWrite {
	int workerNum;
	Socket csocket = null;
	DataInputStream di = null;
	DataOutputStream dos = null;

	public WorkerThread(Socket cs, int workerNum) {
		this.csocket = cs;
		this.workerNum = workerNum;
		try {
			di = new DataInputStream(csocket.getInputStream());
			dos = new DataOutputStream(csocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("��ȡsocket��������ʧ��");
			try {
				cs.close();
				RpcServer.removeWorker(csocket.getInetAddress() + ""
						+ csocket.getPort() + "");
				ForEach.forEachMap(RpcServer.workerSocketmap);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println("�����߳�����");
	}

	public WorkerThread(Socket cs) {
		this.csocket = cs;
		try {
			di = new DataInputStream(csocket.getInputStream());
			dos = new DataOutputStream(csocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("��ȡsocket��������ʧ��");
			RpcServer.removeWorker(csocket.getInetAddress() + ""
					+ csocket.getPort() + "");
			ForEach.forEachMap(RpcServer.workerSocketmap);
			e.printStackTrace();
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
				Task task = readMessage();
				if (task != null) {// ������Ϣ��worker�ڵ�
					int wn = RpcServer.countName;
					System.out.println("�����" + wn + "����");
					DataOutputStream out = RpcServer.getWorker(0);
					sendTask(out, task);
				}
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

	@Override
	public void sendMessage(String str) throws IOException {
		dos.write((byte) 3);
		dos.write((byte) str.getBytes().length);
		dos.write(str.getBytes(), 0, str.getBytes().length);
	}

	private void sendTask(DataOutputStream dout, Task task) {
		try {
			dout.write((byte) 3);
			dout.write((byte) task.getClazz().getBytes().length);
			dout.write((task.getClazz()).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writemululist(String list) throws IOException {
		dos.write((byte) CmdFlag.responseforfile);
		dos.write((byte) list.getBytes().length);
		dos.write(list.getBytes());
	}

	@Override
	public Task readMessage() throws IOException {
		Task task = null;
		byte[] data = null;
		int datatype = di.readByte();
		System.out.println("datatype" + datatype);
		if (datatype == 1) {
			System.out.println("����һ��������Ϣ");
			return null;
		} else if (datatype == 2) {
			RpcServer.addWorker(dos);
			return null;
		} else if (datatype == 3) {
			int datalength = di.readByte();
			data = new byte[datalength];
			di.read(data, 0, datalength);
			System.out.println(datalength);
			String datastr = new String(data);
			data = null;
			task = new Task(datastr);
		} else if (datatype == CmdFlag.dataserverregister) {
			RpcServer.addDataServer(dos);
		} else if (datatype == CmdFlag.dataServerbeat) {
			System.out.println("�������ݷ�����������");
		} else if (datatype == CmdFlag.downloadfile) {
			System.out.println("�����ļ������");

		} else if (datatype == CmdFlag.askmulu) {
			int length = di.readByte();
			data = new byte[length];
			di.read(data, 0, length);
			String path = new String(data);
			String filelist = RpcServer.memoryfilesystem.ls(path);
			System.out.println(filelist);
			writemululist(filelist);
		} else if (datatype == CmdFlag.creatdir) {
			String path = getDataString();
			String[] temp = path.split(",");
			RpcServer.memoryfilesystem.createdir(temp[0], temp[1]);
			String filelist = RpcServer.memoryfilesystem.ls(temp[0]);
			System.out.println(filelist);
		} else if (datatype == CmdFlag.createfile) {
			String path = getDataString();
			String[] temp = path.split(",");
			RpcServer.memoryfilesystem.createfile(temp[0], temp[1]);
			String filelist = RpcServer.memoryfilesystem.ls(temp[0]);
			System.out.println(filelist);
		} else if (datatype == CmdFlag.delete) {
			String path = getDataString();
			String[] temp = path.split(",");
			System.out.println("templength"+temp.length);
			System.out.println("temp[0]"+temp[0]);
			System.out.println("temp[1]"+temp[1]);
			RpcServer.memoryfilesystem.delete(temp[0], temp[1]);
			String filelist = RpcServer.memoryfilesystem.ls(temp[0]);
			System.out.println(filelist);
		}

		return task;

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
}
