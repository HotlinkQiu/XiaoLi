package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.CenterManager;

@WebServlet("/Info")
public class Buffer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Buffer() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		int pno = Integer.parseInt(request.getParameter("pno"));
		
		String pib = CenterManager.getInstance().getBufferByCode(code, 0, pno);
		String ppb = CenterManager.getInstance().getBufferByCode(code, 1, pno);
		String psb = CenterManager.getInstance().getBufferByCode(code, 2, pno);
		JSONObject bufferJSON = constructJSON(pib, ppb, psb);
		
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().print(bufferJSON);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	private JSONObject constructJSON(String pib, String ppb, String psb) {
		JSONObject bufferJSON = new JSONObject();
		bufferJSON.put("info", pib);
		bufferJSON.put("proc", ppb);
		bufferJSON.put("solver", psb);
		return bufferJSON;
	}
}
