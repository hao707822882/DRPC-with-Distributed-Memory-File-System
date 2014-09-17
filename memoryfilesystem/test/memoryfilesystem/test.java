package memoryfilesystem;

public class test {

	public static void main1(String[] args) {
		String []temp="/root/zhu/ming/".split("/");
		System.out.println(temp.length);
		for (int i = 0; i < temp.length; i++) {
			System.out.println(temp[i]);
		}
	}
	
	
	public static void main2(String[] args) {
		int i=-1;
		System.out.println(i==(-1));
	}
	
	
	public static void main3(String[] args) {
		test t=new test();
		try {
			t.ex();
		} catch (Exception e) {
			System.out.println("xx");
			e.printStackTrace();
		}
	}
	
	
	void ex(){
		throw new IllegalArgumentException("xx");
	}
	
	public static void main(String[] args) {
		String name="Öéº£×âºÏ×â";
		byte[] data=null;
		data=name.getBytes();
		data=new byte[4];
		for (int i = 0; i < data.length; i++) {
			System.out.println((int)data[i]);
		}
		
		
	}
}
