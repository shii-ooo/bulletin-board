package practice_webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class PracticeWebappIsDeletePost
 */
@WebServlet("/practice_webapp/admin/create/is_delete_post")
public class PracticeWebappIsDeletePost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    if("削除".equals(request.getParameter("submit"))){
	        String postNumber = request.getParameter("postNumber");
	        
	        String sql = "DELETE posts WHERE post_number = ?";

            try (Connection connection = DatabaseUtil.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);){
                
                statement.setString(1, postNumber);
                
                int rows = statement.executeUpdate();
                
                if (rows == 0) {
                    request.setAttribute("errMsg", "掲示板が削除できませんでした もう一度ためしてください");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/isDelete.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
	    }
	    String threadId = request.getParameter("threadId");
	    response.sendRedirect("../bbs?id=" + threadId);
	}

}
