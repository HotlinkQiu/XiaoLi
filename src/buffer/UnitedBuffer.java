package buffer;

public class UnitedBuffer {
	private ProblemInfoBuffer pib;
	private ProblemProcBuffer ppb;
	private ProblemSolverBuffer psb;
	
	public UnitedBuffer(String paper) {
		pib = new ProblemInfoBuffer(paper);
		ppb = new ProblemProcBuffer();
		psb = new ProblemSolverBuffer();
	}
	
	public ProblemInfoBuffer getPIB() {
		return pib;
	}
	
	public ProblemProcBuffer getPPB() {
		return ppb;
	}
	
	public ProblemSolverBuffer getPSB() {
		return psb;
	}
}
