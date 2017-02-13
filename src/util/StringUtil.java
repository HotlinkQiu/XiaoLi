package util;

public class StringUtil {
	public static String deBracket(String str) {
		String deb = "";
		if((str.startsWith("[") && str.endsWith("]"))
		|| (str.startsWith("(") && str.endsWith(")"))
		|| (str.startsWith("{") && str.endsWith("}"))) {
			deb = str.substring(1, str.length()-1);
		} else {
			deb = str;
		}
		return deb;
	}
}
