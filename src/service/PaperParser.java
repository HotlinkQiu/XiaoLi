package service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import buffer.UnitedBuffer;
import model.Log;
import model.MainStep;

public class PaperParser {
	private long lastTimeFileSize;
	
	private String paper;
	private MainStep msName;
	private int mainStep;
	private List<String> mainLog;
	
	private UnitedBuffer unBuffer;
	
	public PaperParser(String paper) {
		this.paper = paper;
		lastTimeFileSize = 0;
		msName = MainStep.小理就绪;
		mainStep = 0;
		mainLog = new ArrayList<String>();
	}
	
	public void poll() {
		String[] logs = getUpdateLog().split("\n");
		
		for(int i = 0; i < logs.length; i ++) {
			Log log = new Log(logs[i]);
			switch(log.getLogType()) {
			case Main:
				parseMain(log);
				break;
			case PaperInfo:
				break;
			case ProblemInfo:
				break;
			case 试题理解:
				break;
			case 试题求解:
				break;
			case 批改:
				break;
			default:
				break;
			}
		}
	}
	
	private void parseMain(Log log) {
		mainLog.add(log.getContent());
	}
	
	private void parsePaperInfo(Log log) {
		
	}
	
	private String getUpdateLog() {
		String output = "";
		try {
			RandomAccessFile logFile = new RandomAccessFile(getLogFile(), "rw");
			logFile.seek(lastTimeFileSize);
			String temp = "";
			while((temp = logFile.readLine()) != null) {
				output += new String(temp.getBytes("ISO08859-1"), "utf-8")+"\n";
			}
			lastTimeFileSize = logFile.length();
			logFile.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	private File getLogFile() {
		File logFile = new File("/output.log");
		return logFile;
	}
}
