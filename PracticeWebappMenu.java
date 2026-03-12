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
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Servlet implementation class PracticeWebappMenu
 */
@WebServlet("/practice_webapp/admin/menu")
public class PracticeWebappMenu extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sql = "SELECT threads.thread_id, threads.thread_title, COUNT(posts.post_number) AS post_count,categorys.category_name FROM threads LEFT JOIN posts ON threads.thread_id = posts.thread_id "
                + "INNER JOIN categorys ON threads.category_id = categorys.category_id GROUP BY threads.thread_id, threads.thread_title, categorys.category_name ORDER BY post_count DESC OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY;";

        String page = request.getParameter("page");

        try (Connection connection = DatabaseUtil.getConnection();) {
            
            ArrayList<ThreadTitle> threads = new ArrayList<ThreadTitle>();

            try (PreparedStatement statement = connection.prepareStatement(sql);) {

                if (page == null || page.equals("0")) {
                    statement.setInt(1, 0);
                } else {
                    statement.setInt(1, Integer.parseInt(page) * 20);
                }

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int threadId = resultSet.getInt("thread_id");
                    String title = resultSet.getString("thread_title");
                    String categoryName = resultSet.getString("category_name");
                    ThreadTitle threadtitle = new ThreadTitle(threadId, title, categoryName);
                    threads.add(threadtitle);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            String countPostSql = "SELECT COUNT(*) AS count_threads FROM threads";
            int countPosts = 0;

            try (PreparedStatement statement = connection.prepareStatement(countPostSql);) {

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    countPosts = resultSet.getInt("count_threads");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            request.setAttribute("threads", threads);
            request.setAttribute("countPosts", countPosts);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/menu.jsp");
        dispatcher.forward(request, response);

    }

}