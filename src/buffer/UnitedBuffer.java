package buffer;

public class UnitedBuffer {
	private ProblemInfoBuffer pib;
	private ProblemProcBuffer ppb;
	private ProblemSolverBuffer psb;
	
	public UnitedBuffer() {
		pib = new ProblemInfoBuffer();
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
