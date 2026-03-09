package practice_webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class PracticeWebappPost
 */
@MultipartConfig
@WebServlet("/practice_webapp/admin/create/post")
public class PracticeWebappPost extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String inputPost = request.getParameter("post");
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("id");
        String threadId = request.getParameter("threadId");
        Part part = request.getPart("img");

        if (inputPost == null || inputPost.equals("")) {
            request.setAttribute("errMsg", "エラー：本文を入力してください");
            request.setAttribute("threadId", threadId);
        } else {

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                    + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                    + "password=SQLPassword1234";

            String userName = null;
            String threadTitle = null;

            try (Connection connection = DriverManager.getConnection(url);) {

                //
                // user_nameを取得
                //
                String userNameSql = "SELECT user_name FROM users WHERE user_display_id = ?";

                try (PreparedStatement statement = connection.prepareStatement(userNameSql);) {
                    statement.setString(1, userId);

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        userName = resultSet.getString("user_name");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //
                // thread_titleを取得
                //
                String threadTitleSql = "SELECT thread_title FROM threads WHERE thread_id = ?";

                try (PreparedStatement statement = connection.prepareStatement(threadTitleSql);) {
                    statement.setString(1, threadId);

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        threadTitle = resultSet.getString("thread_title");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //
                // 投稿の表示データを送る
                //
                Calendar calendar = Calendar.getInstance();
                Post post;

                String imgName = part.getSubmittedFileName();
                if (imgName != null && !imgName.equals("")) {
                    String imgPath = getServletContext().getRealPath("/img");
                    part.write(imgPath + File.separator + imgName);
                    post = new Post(userName, threadTitle, Integer.parseInt(threadId), calendar.getTime(), inputPost,
                            imgName);
                } else {
                    post = new Post(userName, threadTitle, Integer.parseInt(threadId), calendar.getTime(), inputPost);
                }

                request.setAttribute("post", post);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/post.jsp");
        dispatcher.forward(request, response);
    }

}