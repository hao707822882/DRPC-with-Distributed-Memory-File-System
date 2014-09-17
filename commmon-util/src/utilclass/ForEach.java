package utilclass;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ForEach {

	public static void forEachMap(Map map) {
		Set<String> key = map.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			System.out.println("key «"+s);
			System.out.println("object «"+map.get(s));
		}
	}

}
