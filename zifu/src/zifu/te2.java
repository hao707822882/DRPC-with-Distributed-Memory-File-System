package zifu;

public class te2 {

	
	public static void main(String[] args) {
		zifu.UTF2GBK ug=new UTF2GBK();
		
		String a="严";
		System.out.println(Integer.toHexString((int)a.charAt(0)));
		System.out.println(ug.utf8ToUnicode(a));
		System.out.println(ug.GBK2Unicode(a));
	}
}
