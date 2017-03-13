package buffer;

import java.util.ArrayList;
import java.util.List;

public class ProblemInfoBuffer {
	private List<PInfo> problemInfo;
	private String paper;

	public ProblemInfoBuffer(String paper) {
		problemInfo = new ArrayList<PInfo>();
		this.paper = paper;
	}
	
	public void addProblemInfo(int pno, boolean isSel, String info) {
		if(pno == problemInfo.size()) {
			problemInfo.add(new PInfo(isSel));
		}
		
		PInfo pInfo = problemInfo.get(pno);
		handleInfo(pInfo, info);
	}
	
	private void handleInfo(PInfo pInfo, String info) {
		String[] elements = info.split("\t");

		if(elements[0].equals("[背景]")) {
			pInfo.background = elements[1];
		} else if(elements[0].equals("[题干]")) {
			pInfo.stem = elements[1];
		} else if(elements[0].equals("[组合型选项]")) {
			pInfo.isMixed = Boolean.parseBoolean(elements[1]);
		} else if(elements[0].startsWith("[Q_")) {
			pInfo.questions.add(elements[2]);
		} else if(elements[0].startsWith("[选项")) {
			pInfo.options.add(elements[1]);
		} else if(elements[0].equals("[图]")) {
			pInfo.images.add(elements[1]);
		} else if(elements[0].equals("[大背景]")) {
			if(!elements[1].equals("null")) {
				pInfo.background = elements[1]+"<br/>";
			} else {
				pInfo.background = "";
			}
		} else if(elements[0].equals("[小背景]")) {
			pInfo.background = pInfo.background + elements[1];
		} else if(elements[0].equals("[问题]")) {
			pInfo.stem = elements[1];
		}
	}

	public String getProblemInfo(int pno) {
		PInfo pInfo = problemInfo.get(pno);
		String info = "";
		if(pInfo.isSel) {
			info += pInfo.background+"<br />";
			info += pInfo.stem+"<br />";
			if(pInfo.isMixed) {
				for(int i = 0; i < pInfo.questions.size(); i ++) {
					int no = i + 1;
					info += no+"："+pInfo.questions.get(i)+"<br />";
				}
				info += "A："+pInfo.options.get(0)+"<br />";
				info += "B："+pInfo.options.get(1)+"<br />";
				info += "C："+pInfo.options.get(2)+"<br />";
				info += "D："+pInfo.options.get(3)+"<br />";
			} else {
				info += "A："+pInfo.questions.get(0)+"<br />";
				info += "B："+pInfo.questions.get(1)+"<br />";
				info += "C："+pInfo.questions.get(2)+"<br />";
				info += "D："+pInfo.questions.get(3)+"<br />";
			}
		} else {
			info += pInfo.background+"<br />";
			info += pInfo.stem+"<br />";
		}
		for(String image : pInfo.images) {
			info += "<a href='./label.html?img="+image+"' target=_blank>";
			info += "<img src='./resources/imginfo/"+paper+"/image/" + image +"' />";
			info += "</a>";
		}
		return info;
	}
	
	class PInfo {
		boolean isSel;
		String background;
		String stem;
		boolean isMixed;
		List<String> questions;
		List<String> options;
		List<String> images;
		
		PInfo(boolean isSel) {
			this.isSel = isSel;
			if(isSel) {
				options = new ArrayList<String>();
				questions = new ArrayList<String>();
			}
			images = new ArrayList<String>();
		}
	}
}
