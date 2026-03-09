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
import java.util.ArrayList;

/**
 * Servlet implementation class PracticeWebappFind
 */
@WebServlet("/practice_webapp/admin/find")
public class PracticeWebappFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        String find = request.getParameter("find");
        String category = request.getParameter("category");
        int categoryId = 0;
        if (category.equals("days")) {
            categoryId = 1;
        } else if (category.equals("dinner")) {
            categoryId = 2;
        } else if (category.equals("lanch")) {
            categoryId = 3;
        } else if (category.equals("sapporo")) {
            categoryId = 4;
        } else if (category.equals("hokkaido")) {
            categoryId = 5;
        }

        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                + "password=SQLPassword1234";

        if (find == null || find.equals("")) {
            request.setAttribute("errFind", "検索ワードを入力してください");
        } else {
            try (Connection connection = DriverManager.getConnection(url);) {
                
                //
                //検索
                //
                String findSql = "SELECT threads.thread_id, threads.thread_title, COUNT(posts.post_number) AS post_count,categorys.category_name "
                        + "FROM threads LEFT JOIN posts ON threads.thread_id = posts.thread_id INNER JOIN categorys ON threads.category_id = categorys.category_id "
                        + "WHERE thread_title LIKE ? GROUP BY threads.thread_id, threads.thread_title, categorys.category_name , threads.category_id "
                        + "HAVING threads.category_id = ? ORDER BY post_count DESC;";
                
                try (PreparedStatement statement = connection.prepareStatement(findSql);) {

                    statement.setString(1, "%" + find + "%");
                    statement.setInt(2, categoryId);

                    ResultSet resultSet = statement.executeQuery();

                    ArrayList<ThreadTitle> threadTitles = new ArrayList<ThreadTitle>();

                    while (resultSet.next()) {
                        int threadId = resultSet.getInt("thread_id");
                        String title = resultSet.getString("thread_title");
                        String categoryName = resultSet.getString("category_name");
                        ThreadTitle threadtitle = new ThreadTitle(threadId, title, categoryName);
                        threadTitles.add(threadtitle);
                    }

                    request.setAttribute("threadTitles", threadTitles);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                //
                //検索スレッドの数
                //
                String countPostSql = "SELECT COUNT(*) AS count_threads FROM threads WHERE thread_title LIKE ?";
                int countPosts = 0;

                try (PreparedStatement statement = connection.prepareStatement(countPostSql);) {
                    
                    statement.setString(1, "%" + find + "%");

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        countPosts = resultSet.getInt("count_threads");
                    }
                    
                    request.setAttribute("countPosts", countPosts);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/menu.jsp");
        dispatcher.forward(request, response);
    }

}