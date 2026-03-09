package practice_webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Servlet implementation class PracticeWebappLogin
 */
@WebServlet("/practice_webapp/admin/login")
public class PracticeWebappLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if("会員登録".equals(request.getParameter("submit"))) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/intoUser.jsp");
            dispatcher.forward(request, response);
        }else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/login.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String pw = DigestUtils.sha256Hex(request.getParameter("pw"));

        if (id == null || id.equals("")) {
            request.setAttribute("errId", "IDが未入力です");
            doGet(request,response);
        } else if (pw == null || pw.equals("")) {
            request.setAttribute("errPw", "パスワードが未入力です");
            doGet(request,response);
        } else {

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                    + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                    + "password=SQLPassword1234";

            String sql = "SELECT * FROM users WHERE user_display_id = ? AND pw = ?";

            try (Connection connection = DriverManager.getConnection(url);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, id);
                statement.setString(2, pw);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("UserId", id);
                    if("admin".equals(id)) {
                        session.setAttribute("admin", true);
                    }
                    response.sendRedirect("menu");
                } else {
                    request.setAttribute("errId", "ログインできませんでした もう一度入力してください");
                    doGet(request,response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}