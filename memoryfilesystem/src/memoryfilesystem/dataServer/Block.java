package memoryfilesystem.dataServer;

public class Block {

	private byte[] data;

	public Block(byte[] data) {
		this.data = data;
	}

	public void writeData(byte[] data) {
		this.data = data;
	}

	public byte[] readData() {
		return this.data;
	}
}
