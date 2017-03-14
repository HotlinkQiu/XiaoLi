package service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import util.PathConfig;

public class CenterManager {
	private static CenterManager inst = null;
	private static int num = 0;
	
	private Map<String, Integer> clientCodeNumMap;
	private List<PaperParser> paperParsers;
	
	public static CenterManager getInstance() {
		if(inst == null) {
			synchronized(CenterManager.class) {
				if(inst == null) {
					inst = new CenterManager();
				}
			}
		}
		return inst;
	}
	
	private CenterManager() {
		clientCodeNumMap = new HashMap<String, Integer>();
		paperParsers = new ArrayList<PaperParser>();
	}
	
	public void lunch(String code, String paper) {
		setCode(code);
		paperParsers.add(new PaperParser(paper));
	}
	
	private void setCode(String code) {
		if(codeExists(code)) {
			System.out.println("CODE EXIST!");
			return;
		}
		clientCodeNumMap.put(code, num ++);
	}
	
	public int getNumByCode(String code) {
		if(codeExists(code)) {
			return clientCodeNumMap.get(code);
		} else {
			return -1;
		}
	}
	
	public boolean codeExists(String code) {
		return clientCodeNumMap.containsKey(code);
	}
	
	public JSONObject pollByCode(String code) {
		JSONObject mainInfoJSON = paperParsers.get(getNumByCode(code)).poll();
		return mainInfoJSON;
	}
	
	public String getBufferByCode(String code, int type, int pno) {
		String bufferInfoJSON = paperParsers.get(getNumByCode(code)).getBufferByType(type, pno);
		return bufferInfoJSON;
	}
	
	public String getImg(String paper, String num) {
		String img = "<img src='"+PathConfig.getImgRootPah()+paper+"/image/"+num+"' />";
		return img;
	}
	
	public String getImgLabel(String paper, String num) {
		String imgLabel = "";
		try {
			BufferedReader bufR = new BufferedReader(new InputStreamReader(
				new FileInputStream(PathConfig.getImgLabelRootPath()+paper+"/"+num), "utf-8"));
			String line = "";
			while((line = bufR.readLine()) != null) {
				imgLabel += line + "<br />";
			}
			bufR.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return imgLabel;
	}
	
	public void pollForCheck(String code) {
		paperParsers.get(getNumByCode(code)).checkIntoMainLog();
	}
}
