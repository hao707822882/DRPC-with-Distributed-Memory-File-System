package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import memoryfilesystem.metaServer.FileSystem;
import memoryfilesystem.metaServer.Node;

public class RpcServer {
	ServerSocket ss;
	public static Map workerSocketmap = new HashMap();
	public static Map tasksendclientsocketmap = new HashMap();
	public static Map dataserverSocketmap = new HashMap();
	public static int countName = 0; // worker�����֣����� ������
	private static int workerNum = 0; // woker����������ʱ�仯
	public static FileSystem memoryfilesystem = new FileSystem();

	static {
		Node dir1 = new Node(0, "�����ļ���", null);
		Node file1 = new Node(1, "ming���ļ�", "��127.0.0.1:9999//zhu");
		Node file2 = new Node(1, "ming���ļ�1", "��127.0.0.1:9999//zhu");
		Node file3 = new Node(1, "ming���ļ�2", "��127.0.0.1:9999//zhu");

		dir1.addChildNode(file1);
		dir1.addChildNode(file2);
		dir1.addChildNode(file3);
		file1.setFather(dir1);

		memoryfilesystem.addNode(dir1);
		memoryfilesystem.ls("�����ļ���/");
	}

	@SuppressWarnings("all")
	public void bootStart() {
		try {
			ss = new ServerSocket(9999);
			while (true) {
				Socket cs = ss.accept();
				WorkerThread wt = new WorkerThread(cs, countName);
				new Thread(wt).start();
			}
		} catch (IOException e) {
			try {
				ss.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				new IllegalAccessException("serversocket�����쳣");
			}
		}
	}

	/***
	 * ʹ��ͬ���ķ�ʽ�Ƴ�������worker�����ͬʱ���Ƴ�����Ӳ����� 0 ��ʾ�ɹ� 1 ��ʾʧ��
	 ***/
	public static int removeWorker(String name) {
		int result = 1;
		synchronized (workerSocketmap) {
			workerSocketmap.remove(name);
			result = 0;
			workerNum--;
		}
		return result;
	}

	/***
	 * 
	 * ʹ��ͬ���ķ�ʽ��worker������ӹ����ڵ㣨ͬ�������� 0 ��ʾ�ɹ� 1 ��ʾʧ��
	 * 
	 * ***/

	public static int addWorker(DataOutputStream dout) {
		int result = 1;
		synchronized (workerSocketmap) {
			System.out.println("ע��һ��");
			System.out.println("ע���һ���ڵ���" + countName);
			workerSocketmap.put(0, dout);
			result = 0;
			countName++;
			workerNum++;
		}
		return result;
	}

	/****
	 * 
	 * ��worker���л��һ����ͬ��������
	 * 
	 * ****/
	public static DataOutputStream getWorker(int num) {
		DataOutputStream dout = null;
		synchronized (workerSocketmap) {
			dout = (DataOutputStream) workerSocketmap.get(num);
			return dout;
		}

	}

	/***
	 * 
	 * ���dataserver��map�У��Ա㷵�ظ��ͻ���dataserver�ĵ�ַ 1 ��ʾ���ʧ�� 0��ʾ��ӳɹ�
	 * ***/

	public static int addDataServer(DataOutputStream dout) {
		int result = 1;
		synchronized (dataserverSocketmap) {
			System.out.println("�����һ�����ݷ�����");
			dataserverSocketmap.put(0, dout);
			result = 0;
		}
		return result;
	}

	/***
	 * 
	 * ������ ����server������
	 * 
	 * ***/
	public static void main(String[] args) {
		RpcServer rpc = new RpcServer();
		rpc.bootStart();
	}
}
