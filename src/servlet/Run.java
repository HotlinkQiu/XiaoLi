package servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CenterManager;
import simulation.OutputSimulator;
import util.PathConfig;

@WebServlet("/Run")
public class Run extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Run() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Run!");
		request.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		String paper = request.getParameter("paper");
		if(paper == null || code == null) {
			System.out.println("ERROR: paper is " + paper);
			System.out.println("ERROR: code is " + code);
			return;
		}
		
		if(paper.equals("")) paper = "Test_A";
		System.out.println("Doing " + paper + " with Code: " + code);
		CenterManager.getInstance().lunch(code, paper);
		lunch(paper);
//		OutputSimulator.outputToLog(paper);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void lunch(String paper) {
		String command = PathConfig.getRunPath()+" "+paper+ ".xml";
		System.out.println(command);
		
		Process proc;
		try {
			proc = Runtime.getRuntime().exec(command);
			try {
				proc.waitFor();
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
			InputStream in = proc.getInputStream();
			InputStream err = proc.getErrorStream();

			byte b[] = new byte[in.available()];
			in.read(b, 0, b.length);
			System.out.println(new String(b));
			
			byte c[] = new byte[err.available()];
			err.read(c, 0, c.length);
			System.out.println(new String(c));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
