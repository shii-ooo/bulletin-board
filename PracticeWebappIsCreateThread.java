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
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Servlet implementation class PracticeWebappIsCreateThread
 */
@WebServlet("/practice_webapp/admin/create/is_create_thread")
public class PracticeWebappIsCreateThread extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ("作成".equals(request.getParameter("submit"))) {
            String threadTitle = request.getParameter("threadTitle");
            String categoryName = request.getParameter("categoryName");
            String post = request.getParameter("post");
            String img = request.getParameter("img");
            int categoryId = 0;
            if ("日常".equals(categoryName)) {
                categoryId = 1;
            } else if ("ディナー".equals(categoryName)) {
                categoryId = 2;
            } else if ("ランチ".equals(categoryName)) {
                categoryId = 3;
            } else if ("札幌".equals(categoryName)) {
                categoryId = 4;
            } else if ("北海道".equals(categoryName)) {
                categoryId = 5;
            }
            
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                    + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                    + "password=SQLPassword1234";

            HttpSession session = request.getSession(false);
            String userDisplayId = (String) session.getAttribute("id");
            String userId = null;
            String threadId = null;

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(calendar.getTime());

            try (Connection connection = DriverManager.getConnection(url);) {

                //
                // ユーザーIDを取得
                //
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

                //
                // スレッドを作る
                //
                String threadSql = "INSERT INTO threads(thread_title, user_id, date, category_id) VALUES(?, ?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(threadSql);) {

                    statement.setString(1, threadTitle);
                    statement.setString(2, userId);
                    statement.setString(3, date);
                    statement.setInt(4, categoryId);

                    int rows = statement.executeUpdate();

                    if (rows == 0) {
                        request.setAttribute("errMsg", "作成できませんでした");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("create_thread");
                        dispatcher.forward(request, response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //
                // 作ったスレッドidを取得
                //
                String threadIdSql = "SELECT thread_id FROM threads WHERE thread_title = ?";

                try (PreparedStatement statement = connection.prepareStatement(threadIdSql);) {
                    statement.setString(1, threadTitle);

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        threadId = resultSet.getString("thread_id");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //
                // 投稿を作る
                //

                if (img == null || img.equals("") || img.equals("null")) {

                    String postSql = "INSERT INTO posts(user_id, thread_id, date, post) VALUES(?, ?, ?, ?)";

                    try (PreparedStatement statement = connection.prepareStatement(postSql);) {
                        statement.setString(1, userId);
                        statement.setString(2, threadId);
                        statement.setString(3, date);
                        statement.setString(4, post);

                        int rows = statement.executeUpdate();

                        if (rows == 0) {
                            request.setAttribute("errMsg", "作成できませんでした");
                            request.setAttribute("thread_id", threadId);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("create_thread");
                            dispatcher.forward(request, response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                            request.setAttribute("errMsg", "作成できませんでした");
                            request.setAttribute("thread_id", threadId);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("create_thread");
                            dispatcher.forward(request, response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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