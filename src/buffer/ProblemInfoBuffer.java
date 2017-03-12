package buffer;

import java.util.ArrayList;
import java.util.List;

public class ProblemInfoBuffer {
	private List<String> problemInfo;

	public ProblemInfoBuffer() {
		problemInfo = new ArrayList<String>();
	}
	
	public void addProblemInfo(int pno, String info) {
		if(pno == problemInfo.size()) {
			problemInfo.add(info);
		} else {
			problemInfo.set(pno, problemInfo.get(pno)+"<br />"+info);
		}
	}
	
	public String getProblemInfo(int pno) {
		return problemInfo.get(pno);
	}
}
