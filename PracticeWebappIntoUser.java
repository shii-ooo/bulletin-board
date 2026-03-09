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
import java.sql.ResultSet;

/**
 * Servlet implementation class PracticeWebappIntoUser
 */
@WebServlet("/practice_webapp/admin/into_user")
public class PracticeWebappIntoUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String id = request.getParameter("id");
        String pw = request.getParameter("pw");

        if (userName == null || userName.equals("")) {
            request.setAttribute("errUserName", "ユーザー名が未入力です");
        } else if (id == null || id.equals("")) {
            request.setAttribute("errId", "IDが未入力です");
        } else if (pw == null || pw.equals("")) {
            request.setAttribute("errPw", "パスワードが未入力です");
        } else {

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                    + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                    + "password=SQLPassword1234";

            try (Connection connection = DriverManager.getConnection(url);) {
                
                String userNameSql = "SELECT * FROM users WHERE user_name = ?";
                
                try (PreparedStatement statement = connection.prepareStatement(userNameSql);) {

                    statement.setString(1, userName);

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        request.setAttribute("errUserName", "重複したユーザー名が存在します ユーザー名を変更してください");
                        RequestDispatcher dispatcher = request
                                .getRequestDispatcher("/WEB-INF/practice_webapp/intoUser.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                String displayIdSql = "SELECT * FROM users WHERE user_display_id = ?";
                
                try (PreparedStatement statement = connection.prepareStatement(displayIdSql);) {

                    statement.setString(1, id);

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        request.setAttribute("errId", "重複したidが存在します idを変更してください");
                        RequestDispatcher dispatcher = request
                                .getRequestDispatcher("/WEB-INF/practice_webapp/intoUser.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            request.setAttribute("userName", userName);
            request.setAttribute("id", id);
            request.setAttribute("pw", pw);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/isIntoUser.jsp");
            dispatcher.forward(request, response);
            return;

        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/intoUser.jsp");
        dispatcher.forward(request, response);
    }

}