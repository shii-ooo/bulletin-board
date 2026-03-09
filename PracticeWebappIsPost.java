package practice_webapp;

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
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class PracticeWebappIsPost
 */
@WebServlet("/practice_webapp/admin/create/is_post")
public class PracticeWebappIsPost extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ("投稿".equals(request.getParameter("submit"))) {
            String userDisplayId = request.getParameter("userId");
            String threadId = request.getParameter("threadId");
            String date = request.getParameter("date");
            String post = request.getParameter("post");
            String img = request.getParameter("img");

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                    + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                    + "password=SQLPassword1234";

            String userId = null;

            try (Connection connection = DriverManager.getConnection(url);) {

                String userIdSql = "SELECT user_id FROM users WHERE user_display_id = ?";

                try (PreparedStatement statement = connection.prepareStatement(userIdSql);) {
                    statement.setString(1, userDisplayId);

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        userId = resultSet.getString("user_id");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (img == null || img.equals("") || img.equals("null")) {

                    String postSql = "INSERT INTO posts(user_id, thread_id, date, post) VALUES(?, ?, ?, ?)";

                    try (PreparedStatement statement = connection.prepareStatement(postSql);) {
                        statement.setString(1, userId);
                        statement.setString(2, threadId);
                        statement.setString(3, date);
                        statement.setString(4, post);

                        int rows = statement.executeUpdate();

                        if (rows == 0) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        HttpSession session = request.getSession();
                        session.setAttribute("errPost", "投稿できませんでした");
                        session.setAttribute("post", post);
                        response.sendRedirect("../bbs?id=" + threadId);
                        return;
                    }
                } else {

                    String postSql = "INSERT INTO posts(user_id, thread_id, date, post, img) VALUES(?, ?, ?, ?, ?)";

                    try (PreparedStatement statement = connection.prepareStatement(postSql);) {
                        statement.setString(1, userId);
                        statement.setString(2, threadId);
                        statement.setString(3, date);
                        statement.setString(4, post);
                        statement.setString(5, img);

                        int rows = statement.executeUpdate();

                        if (rows == 0) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        HttpSession session = request.getSession();
                        session.setAttribute("errPost", "投稿できませんでした");
                        session.setAttribute("post", post);
                        response.sendRedirect("../bbs?id=" + threadId);
                        return;
                    }
                }

                response.sendRedirect("../bbs?id=" + threadId);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            response.sendRedirect("../menu");
        }
    }

}