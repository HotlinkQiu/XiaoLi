package buffer;

import java.util.ArrayList;
import java.util.List;

import util.StringUtil;

public class ProblemProcBuffer {
	List<String> problemProc;

	public ProblemProcBuffer() {
		problemProc = new ArrayList<String>();
	}
	
	public void addProblemProc(int pno, String proc) {
		if(isTree(proc)) proc = parseTree(proc);
		if(pno == problemProc.size()) {
			problemProc.add(proc);
		} else {
			problemProc.set(pno, problemProc.get(pno)+"<br/>"+proc);
		}
	}
	
	private boolean isTree(String solver) {
		if(solver.contains("句法树]")) return true;
		return false;
	}
	
	private String parseTree(String solver) {
		String[] elements = solver.split("\t");
		String uri = StringUtil.encodeURIComponent(elements[2]);
		String treeLink = "<a href='./tree.html?sentence=" + uri + "' target='_blank'>" + elements[2] + "</a>";
		return elements[0] + "\t" + elements[1] + "\t" + treeLink;
	}
	
	public String getProblemProc(int pno) {
		return problemProc.get(pno);
	}
}
