package memoryfilesystem.metaServer;

public class FileSystem {

	public static final String FileSystemName = "MemoryFileSystem";

	private Node root = Node.getDirInstence("root");

	private Node nowPartition;

	public Node getRoot() {
		return root;
	}

	public void addNode(Node node) {
		root.addChildNode(node);
	}

	/***
	 * 
	 * 查看目录下的文件列表 path是路径
	 * ***/
	public String ls(String path) {
		System.out.println("ls" + path);
		String[] pathtemp = path.split("/");
		Node tempNode = root;
		for (int i = 0; i < pathtemp.length; i++) {
			tempNode = tempNode.getChildNode(pathtemp[i]);
		}
		return tempNode.foreach();
	}

	/***
	 * 
	 * 到达摸个node节点下
	 * 
	 * ***/
	public Node getToNode(String path) {
		System.out.println(path);
		String[] pathtemp = path.split("/");
		Node tempNode = root;
		for (int i = 0; i < pathtemp.length; i++) {
			tempNode = tempNode.getChildNode(pathtemp[i]);
		}
		System.out.println("明的文件夹/" + tempNode.getNodeName());
		return tempNode;
	}

	public void createdir(String path, String name) {
		Node temp = getToNode(path);
		Node node = Node.getDirInstence(name);
		node.setFather(temp);
		temp.addChildNode(node);
	}

	public void createfile(String path, String name) {
		Node temp = getToNode(path);
		Node node = Node.getFileInstence(name, "127.0.0.1:8888/" + name);
		node.setFather(temp);
		temp.addChildNode(node);
	}

	public void delete(String path, String name) {
		
		System.out.println("我要删除path路径下的文件，path为"+path);
		System.out.println("我要删除path路径下的文件，name为"+name);
		Node temp = getToNode(path);
		temp.delete(name);
	}
}
