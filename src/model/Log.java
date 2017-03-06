package model;

import util.StringUtil;

public class Log {
	private String log;
	
	private String exinfo;
	private LogType type;
	private String subtype;
	private String content;

	public Log(String log) {
		this.log = log;
		parseLog();
	}
	
	private void parseLog() {
		String[] parts = log.split("\t");
		if(parts.length < 3) {
			exinfo = "";
			type = LogType.DEFAULT;
			content = "";
			return;
		}

		exinfo = parts[0];
		type = LogType.valueOf(StringUtil.deBracket(parts[1]));
		subtype = parts[2];
		content = "";
		for(int i = 3; i < parts.length; i ++) {
			content += parts[i]+"\t";
		}
	}
	
	public String getExInfo() {
		return exinfo;
	}
	
	public LogType getLogType() {
		return type;
	}
	
	public String getSubType() {
		return subtype;
	}
	
	public String getContent() {
		return content;
	}
}
