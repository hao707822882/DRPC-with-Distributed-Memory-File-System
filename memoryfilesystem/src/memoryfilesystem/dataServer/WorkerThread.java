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
 * 负责心跳处理 当有客户断开连接时，从server中的workmap中删除该客户端 1是心跳 2注册务 3是任务
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
				System.out.println("流已经关闭");
				e1.printStackTrace();
			}
			System.out.println("获取socket的输入流失败");
			e.printStackTrace();
		}
		System.out.println("工作线程启动");
	}

	/***
	 * 
	 * 完成数据的讀取與處理
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
			System.out.println("有人请求数据了");
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
			System.out.println("上传成功");

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
			System.out.println("上传的数据是" + new String(data));
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
