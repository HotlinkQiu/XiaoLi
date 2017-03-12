package buffer;

import java.util.ArrayList;
import java.util.List;

public class ProblemSolverBuffer {
	private List<String> problemSolver;

	public ProblemSolverBuffer() {
		problemSolver = new ArrayList<String>();
	}
	
	public void addProblemSolver(int pno, String solver) {
		if(pno == problemSolver.size()) {
			problemSolver.add(solver);
		} else {
			problemSolver.set(pno, problemSolver.get(pno)+"<br/>"+solver);
		}
	}
	
	public String getProblemSolver(int pno) {
		return problemSolver.get(pno);
	}
}
