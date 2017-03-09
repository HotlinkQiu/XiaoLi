package buffer;

public class UnitedBuffer {
	private ProblemInfoBuffer pib;
	private ProblemSolverBuffer psb;
	private ProblemResultBuffer prb;
	
	public UnitedBuffer(int pno) {
		pib = new ProblemInfoBuffer(pno);
		psb = new ProblemSolverBuffer(pno);
		prb = new ProblemResultBuffer(pno);
	}
	
	public ProblemInfoBuffer getPIB() {
		return pib;
	}
	
	public ProblemSolverBuffer getPSB() {
		return psb;
	}
	
	public ProblemResultBuffer getPRB() {
		return prb;
	}
}
