package buffer;

import util.StringUtil;

public class ProblemResultBuffer {
	private int pno;
	private int[] resultBuffer;
	private String resultInfo;
	
	public ProblemResultBuffer(int pno) {
		this.pno = pno;
		resultBuffer = new int[pno];
		resultInfo = "";
	}
	
	public void handleResult(String content) {
		String[] parts = content.split("\t");
		int index = StringUtil.getProbelmIndex(parts[0]);
		if(index < 0 || index >= pno) {
			System.out.println("Problem Result Buffer No ERROR!");
			return;
		}
		
		String result = parts[1];
		if(result.startsWith("正确")) {
			resultBuffer[index] = 1;
		} else if(result.startsWith("错误")){
			resultBuffer[index] = -1;
		}
		
		resultInfo = content + "<br />"	+ resultInfo;
	}
}
