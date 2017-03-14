package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.CenterManager;

@WebServlet("/Poll")
public class Poll extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Poll() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		String paper = request.getParameter("paper");
		boolean check = Boolean.parseBoolean(request.getParameter("check"));

		if(paper == null || code == null) {
			System.out.println("ERROR: paper is " + paper);
			System.out.println("ERROR: code is " + code);
			return;
		}
		
		if(paper.equals("")) paper = "Test_A";
		if(check) CenterManager.getInstance().pollForCheck(code);
		JSONObject mainInfoJSON = CenterManager.getInstance().pollByCode(code);
		
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().print(mainInfoJSON);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
