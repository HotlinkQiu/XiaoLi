package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
	
	public static int getProbelmIndex(String str) {
		if(str.startsWith("[P_") && str.endsWith("]")) {
			return Integer.parseInt(str.substring(3, str.length()-1))-1;
		} else {
			return -1;
		}
	}
	
	public static String encodeURIComponent(String str) {
		String result = null;
		try {
			result = URLEncoder.encode(str, "UTF-8")
							   .replaceAll("\\+", "%20")
							   .replaceAll("\\%21", "!")
							   .replaceAll("\\%27", "'")
							   .replaceAll("\\%28", "(")
							   .replaceAll("\\%29", ")")
							   .replaceAll("\\%7E", "~");
		} catch(UnsupportedEncodingException e) {
			result = str;
		}
		return result;
	}
}
