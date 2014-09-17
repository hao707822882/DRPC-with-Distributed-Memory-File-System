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
	public static int countName = 0; // worker的名字，递增 不减少
	private static int workerNum = 0; // woker的数量，随时变化
	public static FileSystem memoryfilesystem = new FileSystem();

	static {
		Node dir1 = new Node(0, "明的文件夹", null);
		Node file1 = new Node(1, "ming的文件", "在127.0.0.1:9999//zhu");
		Node file2 = new Node(1, "ming的文件1", "在127.0.0.1:9999//zhu");
		Node file3 = new Node(1, "ming的文件2", "在127.0.0.1:9999//zhu");

		dir1.addChildNode(file1);
		dir1.addChildNode(file2);
		dir1.addChildNode(file3);
		file1.setFather(dir1);

		memoryfilesystem.addNode(dir1);
		memoryfilesystem.ls("明的文件夹/");
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
				new IllegalAccessException("serversocket创建异常");
			}
		}
	}

	/***
	 * 使用同步的方式移除死亡的worker（如果同时有移除和添加操作） 0 表示成功 1 表示失败
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
	 * 使用同步的方式向worker表中添加工作节点（同步操作） 0 表示成功 1 表示失败
	 * 
	 * ***/

	public static int addWorker(DataOutputStream dout) {
		int result = 1;
		synchronized (workerSocketmap) {
			System.out.println("注册一个");
			System.out.println("注册的一个节点是" + countName);
			workerSocketmap.put(0, dout);
			result = 0;
			countName++;
			workerNum++;
		}
		return result;
	}

	/****
	 * 
	 * 从worker表中获得一个（同步方法）
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
	 * 添加dataserver到map中，以便返回个客户的dataserver的地址 1 表示添加失败 0表示添加成功
	 * ***/

	public static int addDataServer(DataOutputStream dout) {
		int result = 1;
		synchronized (dataserverSocketmap) {
			System.out.println("新添加一个数据服务器");
			dataserverSocketmap.put(0, dout);
			result = 0;
		}
		return result;
	}

	/***
	 * 
	 * 主方法 启动server服务器
	 * 
	 * ***/
	public static void main(String[] args) {
		RpcServer rpc = new RpcServer();
		rpc.bootStart();
	}
}
