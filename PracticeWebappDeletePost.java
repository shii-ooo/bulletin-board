package practice_webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class PracticeWebappDeletePost
 */
@WebServlet("/practice_webapp/admin/create/delete_post")
public class PracticeWebappDeletePost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    request.setAttribute("postNumber", request.getParameter("postNumber"));
        request.setAttribute("threadId", request.getParameter("threadId"));
        request.setAttribute("userName", request.getParameter("userName"));
        request.setAttribute("date", request.getParameter("date"));
        request.setAttribute("post", request.getParameter("post"));
        request.setAttribute("img", request.getParameter("img"));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/deletePost.jsp");
        dispatcher.forward(request, response);
	    
	}

}
