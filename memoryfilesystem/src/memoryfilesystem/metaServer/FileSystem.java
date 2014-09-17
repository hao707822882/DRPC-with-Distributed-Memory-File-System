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
	 * �鿴Ŀ¼�µ��ļ��б� path��·��
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
	 * ��������node�ڵ���
	 * 
	 * ***/
	public Node getToNode(String path) {
		System.out.println(path);
		String[] pathtemp = path.split("/");
		Node tempNode = root;
		for (int i = 0; i < pathtemp.length; i++) {
			tempNode = tempNode.getChildNode(pathtemp[i]);
		}
		System.out.println("�����ļ���/" + tempNode.getNodeName());
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
		
		System.out.println("��Ҫɾ��path·���µ��ļ���pathΪ"+path);
		System.out.println("��Ҫɾ��path·���µ��ļ���nameΪ"+name);
		Node temp = getToNode(path);
		temp.delete(name);
	}
}
