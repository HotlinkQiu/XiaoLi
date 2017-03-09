package buffer;

import java.util.ArrayList;
import java.util.List;

public class ProblemInfoBuffer {
	private List<String> problemInfo;
	public ProblemInfoBuffer(int pno) {
		problemInfo = new ArrayList<String>();
	}
	
	public void addProblemInfo(int pno, String info) {
		problemInfo.set(pno, problemInfo.get(pno)+"<br />"+info);
	}
	
	public String getProblemInfo(int pno) {
		return problemInfo.get(pno);
	}
}
