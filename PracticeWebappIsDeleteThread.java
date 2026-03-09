package practice_webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Servlet implementation class PracticeWebappIsDeleteThread
 */
@WebServlet("/practice_webapp/admin/create/is_delete_thread")
public class PracticeWebappIsDeleteThread extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    if("削除".equals(request.getParameter("submit"))) {
	        
	        String id = request.getParameter("id");
	        
	        try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                    + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                    + "password=SQLPassword1234";
            
            try (Connection connection = DriverManager.getConnection(url);){
                
                //
                //ポストの削除
                //
                String postSql = "DELETE posts WHERE thread_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(postSql);){
                    
                    statement.setString(1, id);
                    
                    statement.executeUpdate();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                //
                //掲示板の削除
                //
                String threadSql = "DELETE threads WHERE thread_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(threadSql);){
                    
                    statement.setString(1, id);
                    
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
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
	    }
	    
	    response.sendRedirect("../menu");
	    
	}

}
