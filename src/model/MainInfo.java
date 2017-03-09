package model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class MainInfo {
	private MainStep msName;
	private int mainStep;
	private int selNo;
	private int saNo;
	private List<String> mainLog;
	private List<Integer> selStatus;
	private List<Integer> saStatus;

	public MainInfo() {
		msName = MainStep.小理就绪;
		mainStep = 0;
		selNo = 0;
		saNo = 0;
		mainLog = new ArrayList<String>();
		selStatus = new ArrayList<Integer>();
		saStatus = new ArrayList<Integer>();
	}
	
	public void setMsName(MainStep msName) {
		this.msName = msName;
	}
	
	public MainStep getMsName() {
		return msName;
	}
	
	public void stepPlus() {
		mainStep ++;
	}
	
	public int getMainStep() {
		return mainStep;
	}
	
	public void setSelNo(int selNo) {
		this.selNo = selNo;
	}
	
	public int getSelNo() {
		return selNo;
	}
	
	public void setSaNo(int saNo) {
		this.saNo = saNo;
	}
	
	public int getSaNo() {
		return saNo;
	}
	
	public void addMainLog(String log) {
		mainLog.add(log);
	}
	
	public void setSelStatus(int pno, int status) {
		if(pno == selStatus.size()) {
			selStatus.add(status);
		} else if(pno < selStatus.size()) {
			selStatus.set(pno, status);
		} else {
			System.out.println("Wrong SEL PNO");
		}
	}
	
	public void setSaStatus(int pno, int status) {
		if(pno == saStatus.size()) {
			saStatus.add(status);
		} else if(pno < saStatus.size()) {
			saStatus.set(pno, status);
		} else {
			System.out.println("Wrong SA PNO");
		}
	}
	
	public JSONObject toJSON() {
		JSONObject mainInfoJSON = new JSONObject();
		mainInfoJSON.put("msName", msName.toString());
		mainInfoJSON.put("mainStep", mainStep);
		mainInfoJSON.put("selNo", selNo);
		mainInfoJSON.put("saNo", saNo);
		String logs = "";
		for(String log : mainLog) {
			logs = log + "<br />" + logs;
		}
		mainInfoJSON.put("mainLog", logs);
		mainInfoJSON.put("selStatus", selStatus);
		mainInfoJSON.put("saStatus", saStatus);
		return mainInfoJSON;
	}
}
