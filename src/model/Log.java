package model;

import util.StringUtil;

public class Log {
	private String log;
	
	private String date;
	private String time;
	private DisplayFrame dFrame;
	private LogType type;
	private String content;

	public Log(String log) {
		this.log = log;
		parseLog();
	}
	
	private void parseLog() {
		String[] parts = log.split("\t");
		if(parts.length < 4) {
			date = "";
			time = "";
			dFrame = DisplayFrame.DEFAULT;
			type =LogType.DEFAULT;
			content = "";
			return;
		}

		date = parts[0];
		time = parts[1];
		dFrame = DisplayFrame.valueOf(StringUtil.deBracket(parts[2]));
		type = LogType.valueOf(StringUtil.deBracket(parts[3]));
		content = "";
		for(int i = 4; i < parts.length; i ++) {
			content += parts[i];
		}
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	public DisplayFrame getDFrame() {
		return dFrame;
	}
	
	public LogType getType() {
		return type;
	}
	
	public String getContent() {
		return content;
	}
}
