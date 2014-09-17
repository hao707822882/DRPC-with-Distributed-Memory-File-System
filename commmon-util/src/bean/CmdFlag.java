package bean;

import java.io.Serializable;

/***
 * 
 * 1��worker���� 2��rpcworkerע�� 3��rpc���� 4��dataserverregister������ 5��dataserverbeat
 * 6�ǿͻ������������������ 7�Ƿ��������ص��������ڵ�blockServer�ĵ�ַ 8��client��block�������� 9�ǿͻ��������������Ŀ¼����
 * 
 * block�����ݿ�Ĵ�С �����涨��Ŀ¼�����ԣ� �� node1��node2 �ָ� node�����ļ��������ã��ָ�
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
