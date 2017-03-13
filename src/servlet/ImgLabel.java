package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.CenterManager;

@WebServlet("/ImageLabel")
public class ImgLabel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ImgLabel() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String paper = request.getParameter("paper");
		String num = request.getParameter("num");

		String img = CenterManager.getInstance().getImg(paper, num);
		String label = CenterManager.getInstance().getImgLabel(paper, num);
		
		JSONObject imgJSON = constructImgJSON(img, label);
		
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().print(imgJSON);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	private JSONObject constructImgJSON(String img, String label) {
		JSONObject imgJSON = new JSONObject();
		imgJSON.put("img", img);
		imgJSON.put("label", label);
		return imgJSON;
	}
}
