import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Test {
	public static void main(String[] args) {

		String xm = "100";
		String ym = "140";
		String m = "0.0233";
		String n = "119.186";
		String r = "30";

		Map<String, String> replace = new HashMap<>();
		replace.put("xm", xm);
		replace.put("ym", ym);
		replace.put("m", m);
		replace.put("n", n);
		replace.put("r", r);

		// String l1 = "(x - xm)² + ((m * x + n) - ym)² = r²";
		// String l1 = "x² - 2 * xm * x + xm² + m² * x² + 2 * m * (n - ym) * x +
		// (n -ym)² = r²";
		// String l1 = "(m² + 1) * x² - 2 * xm * x + 2 * m * (n - ym) * x + xm²
		// + (n - ym)² = r²";
		// String l1 = "(m² + 1) * x² - 2 * xm * x + 2 * m * n * x - 2 * m * ym
		// * x + xm² + (n - ym)² = r²";
		// String l1 = "(m² + 1) * x² + (-2 * xm + 2 * m * n - 2 * m * ym) * x +
		// xm² + (n - ym)² = r²";
		// String l1 = "x² + ((-2 * xm + 2 * m * n - 2 * m * ym) / (m² + 1)) * x
		// + ((xm² + (n - ym)² - r²) / (m² + 1)) = 0 ";
		String l1 = "x² + ((-2 * xm + 2 * m * n - 2 * m * ym) / (m² + 1)) * x + ((xm² + (n - ym)² - r²) / (m² + 1)) = 0 ";

		System.out.println(extracted(replace, l1));

	}

	private static String extracted(Map<String, String> replace, String toReplace) {
		Iterator<String> it = replace.keySet().iterator();
		while (it.hasNext()) {
			String find = it.next();
			String replacement = replace.get(find);

			toReplace = toReplace.replaceAll(find, replacement);
		}
		return toReplace;
	}

}
