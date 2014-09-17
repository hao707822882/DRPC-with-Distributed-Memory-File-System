package memoryfilesystem.metaServer;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/***
 * 
 * 文件树的基本组成部分
 * 
 * type 0为目录 1为文件
 * 
 * 系统要求：文件和目录不能重名
 * 
 * ***/
public class Node {

	public Node(int type, String nodeName, String dataLocation) {
		this.nodeType = type;
		this.nodeName = nodeName;
		this.dataLocation = dataLocation;

	}

	public Node getChildNode(String name) {
		Node childNode = null;
		childNode = (Node) child.get(name);
		if (childNode == null) {
			new FileNotFoundException();
		}
		return childNode;
	}

	public static Node getFileInstence(String name, String dataLocation) {
		return new Node(1, name, dataLocation);
	}

	public static Node getDirInstence(String name) {
		return new Node(0, name, null);
	}

	public void addChildNode(String name, int type, String dataLocation) {
		System.out.println("我添加了一个" + name);
		child.put(name, new Node(type, name, dataLocation));
	}

	public void addChildNode(Node node) {
		child.put(node.getNodeName(), node);
	}

	public String foreach() {
		String file = "";
		Set set = child.keySet();
		for (Object name : set) {
			file = file + ((Node) child.get(name)).getNodeName() + ",";
		}
		return file;
	}

	public void delete(String name) {
		this.child.remove(name);
	}

	public int nodeType;
	public String nodeName;
	public String dataLocation;
	public Node father;
	public Map child = new HashMap();

	public int getNodeType() {
		return nodeType;
	}

	public Node getFather() {
		return father;
	}

	public void setFather(Node father) {
		this.father = father;
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getDataLocation() {
		return dataLocation;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void setDataLocation(String dataLocation) {
		if (this.getNodeType() == 0) {
			System.err.print("这不是一个文件");
			return;
		}
		this.dataLocation = dataLocation;
	}
}
