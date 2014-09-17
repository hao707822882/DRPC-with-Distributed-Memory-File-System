package bean;

import java.io.Serializable;

/***
 * 
 * 1是worker心跳 2是rpcworker注册 3是rpc任务 4是dataserverregister的心跳 5是dataserverbeat
 * 6是客户端向服务器请求数据 7是服务器返回的数据所在的blockServer的地址 8是client向block请求数据 9是客户端向服务器请求目录数据
 * 
 * block是数据块的大小 、、规定：目录数据以， 如 node1，node2 分割 node中有文件和数据用：分隔
 * 
 * ***/
public class CmdFlag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int workerbeat = 1;
	public static final int rpcworkerregister = 2;
	public static final int task = 3;
	public static final int dataserverregister = 4;
	public static final int dataServerbeat = 5;
	public static final int responseforfile = 7;
	public static final int downloadfile = 8;
	public static final int upload = 14;
	public static final int serializabledata = 9;
	public static final int askmulu = 10;
	public static final int createfile = 11;
	public static final int creatdir = 12;
	public static final int delete = 13;

	public static final int blockSize = 1024 * 10;

}
