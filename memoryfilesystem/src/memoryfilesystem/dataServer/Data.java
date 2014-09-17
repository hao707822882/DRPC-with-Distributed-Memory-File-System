package memoryfilesystem.dataServer;

public class Data {

	byte data[];
	String name;

	public Data(byte[] data, String name) {
		this.data = data;
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public String getName() {
		return name;
	}

}
