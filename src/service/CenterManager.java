package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

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
}
