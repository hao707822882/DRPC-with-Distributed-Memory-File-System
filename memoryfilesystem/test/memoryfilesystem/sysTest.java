package memoryfilesystem;

import java.nio.file.Files;

import memoryfilesystem.metaServer.FileSystem;
import memoryfilesystem.metaServer.Node;

public class sysTest {
	public static void main(String[] args) {
		FileSystem fileSystem = new FileSystem();
		Node dir1 = new Node(0, "�����ļ���", null);
		Node file1 = new Node(1, "ming���ļ�", "��127.0.0.1:9999//zhu");
		Node file2 = new Node(1, "ming���ļ�1", "��127.0.0.1:9999//zhu");
		Node file3 = new Node(1, "ming���ļ�2", "��127.0.0.1:9999//zhu");

		dir1.addChildNode(file1);

		dir1.addChildNode(file2);

		dir1.addChildNode(file3);
		file1.setFather(dir1);
		
		fileSystem.addNode(dir1);
		
		fileSystem.getToNode("�����ļ���/");
		//fileSystem.ls("�����ļ���/");
		

	}
}
