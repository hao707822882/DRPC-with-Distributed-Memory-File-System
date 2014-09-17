package zifu;

import java.nio.charset.Charset;

public class te {

	public static void main(String[] args) throws Throwable {
		String aa = "严";
		System.out.println("默认字符集" + Charset.defaultCharset().name());
		System.out.println("自身的unicode编码" + Integer.toHexString(aa.charAt(0)));

		System.out.println("unicode编码");
		byte[] b = aa.getBytes("unicode");

		for (int i = 0; i < b.length; i++) {
			String hexString = Integer.toHexString(((int) b[i]));
			System.out.print(hexString + " ");
		}

		System.out.println();
		System.out.println("utf8编码");
		byte[] c = aa.getBytes("utf8");
		for (int i = 0; i < c.length; i++) {
			String hexString = Integer.toHexString(((int) c[i]));
			System.out.print(hexString + " ");
		}

		System.out.println();
		System.out.println("gbk编码");
		byte[] a = aa.getBytes("gbk");
		for (int i = 0; i < a.length; i++) {
			String hexString = Integer.toHexString(((int) a[i]));
			System.out.print(hexString + " ");
		}

		System.out.println();
		System.out.println("iso-8859-1编码");
		byte[] g = aa.getBytes("iso-8859-1");
		for (int i = 0; i < g.length; i++) {
			String hexString = Integer.toHexString(((int) g[i]));
			System.out.print(hexString + " ");
		}
		System.out.println();

		String send = "严";
		byte[] sendbyte = send.getBytes("gbk");
		String receive = new String(sendbyte, "utf8");
		String zhuan = new String(receive.getBytes("utf8"), "gbk");
		System.out.println("得到的gbk编码");
		for (int i = 0; i < sendbyte.length; i++) {
			String hexString = Integer.toHexString(((int) sendbyte[i]));
			System.out.print(hexString + " ");
		}
		System.out.println();
		
		System.out.println("转码后的编码");
		byte[] f = receive.getBytes();
		for (int i = 0; i < f.length; i++) {
			String hexString = Integer.toHexString(((int) f[i]));
			System.out.print(hexString + " ");
		}
		System.out.println("转换的字符为："+receive);
		
	
		byte[] ff = zhuan.getBytes();
		for (int i = 0; i < ff.length; i++) {
			String hexString = Integer.toHexString(((int) ff[i]));
			System.out.print(hexString + " ");
		}
		System.out.println("专函的字符为："+zhuan);
	}
}
