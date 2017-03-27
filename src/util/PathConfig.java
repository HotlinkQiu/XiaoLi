package util;

public class PathConfig {
//	private static String logFilePath = "D:/Eclipse Workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/XiaoLi/WEB-INF/classes/simulation/";
	private static String logFilePath = "/data/GeoScholar_data/GeoScholar_jar/log/";
	private static String imgRootPath = "./resources/imginfo/";
//	private static String imgLabelRootPath = "D:/Eclipse Workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/XiaoLi/resources/imginfo/";
	private static String imgLabelRootPath = "/usr/local/tomcat8080/webapps/XiaoLi/resources/imginfo/";
	private static String runPath = "/data/GeoScholar_data/GeoScholar_jar/run.sh";
	
	public static String getLogFilePah() {
		return logFilePath;
	}
	
	public static String getImgRootPah() {
		return imgRootPath;
	}
	
	public static String getImgLabelRootPath() {
		return imgLabelRootPath;
	}
	
	public static String getRunPath() {
		return runPath;
	}
}
