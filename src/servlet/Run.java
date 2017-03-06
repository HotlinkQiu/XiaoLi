package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CenterManager;
import simulation.OutputSimulator;

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
		OutputSimulator.outputToLog();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
