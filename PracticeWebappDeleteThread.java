package practice_webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class PracticeWebappDeleteThread
 */
@WebServlet("/practice_webapp/admin/create/delete_thread")
public class PracticeWebappDeleteThread extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    request.setAttribute("id", request.getParameter("threadId"));
        request.setAttribute("title", request.getParameter("threadTitle"));
        request.setAttribute("category", request.getParameter("threadCategory"));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/deleteThread.jsp");
        dispatcher.forward(request, response);
        
	}

}
