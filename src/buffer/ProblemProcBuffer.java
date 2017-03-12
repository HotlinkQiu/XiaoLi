package buffer;

import java.util.ArrayList;
import java.util.List;

public class ProblemProcBuffer {
	List<String> problemProc;

	public ProblemProcBuffer() {
		problemProc = new ArrayList<String>();
	}
	
	public void addProblemProc(int pno, String proc) {
		if(pno == problemProc.size()) {
			problemProc.add(proc);
		} else {
			problemProc.set(pno, problemProc.get(pno)+"<br/>"+proc);
		}
	}
	
	public String getProblemProc(int pno) {
		return problemProc.get(pno);
	}
}
