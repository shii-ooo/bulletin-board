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
 * Servlet implementation class PracticeWebappIsIntoUser
 */
@WebServlet("/practice_webapp/admin/is_into_user")
public class PracticeWebappIsIntoUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if("登録".equals(request.getParameter("submit"))) {
            
            String userName = request.getParameter("userName");
            String id = request.getParameter("id");
            String pw = DigestUtils.sha256Hex(request.getParameter("pw"));
            
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                    + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                    + "password=SQLPassword1234";

            String sql = "INSERT INTO users(user_name, user_display_id , pw) VALUES(?, ? , ?)";

            try (Connection connection = DriverManager.getConnection(url);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, userName);
                statement.setString(2, id);
                statement.setString(3, pw);

                int rows = statement.executeUpdate();

                if (rows != 0) {
                    response.sendRedirect("login");
                } else {
                    request.setAttribute("errId", "登録できませんでした もう一度入力してください");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/intoUser.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }else {
            response.sendRedirect("menu");
        }
    }

}