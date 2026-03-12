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
import java.util.Date;
import java.util.ArrayList;

/**
 * Servlet implementation class PracticeWebappBbs
 */
@WebServlet("/practice_webapp/admin/bbs")
public class PracticeWebappBbs extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection connection = DatabaseUtil.getConnection();){
            
            ArrayList<Post> posts = new ArrayList<Post>();
            String id = request.getParameter("id");
            String page = request.getParameter("page");
            
            //
            //最初の投稿を取得
            //
            String firstpostSql = "SELECT TOP 1 post_number, user_name, thread_title, posts.thread_id, posts.date, post, img FROM posts "
                    + "LEFT JOIN users ON posts.user_id = users.user_id LEFT JOIN threads ON posts.thread_id = threads.thread_id "
                    + "WHERE threads.thread_id = ?";
            
            try(PreparedStatement statement = connection.prepareStatement(firstpostSql);){
                
                statement.setString(1, id);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    int postNumber = resultSet.getInt("post_number");
                    String userName = resultSet.getString("user_name");
                    String threadTitle = resultSet.getString("thread_title");
                    int threadId = resultSet.getInt("thread_id");
                    Date date = resultSet.getTimestamp("date");
                    String postText = resultSet.getString("post");
                    String img = resultSet.getString("img");
                    Post post = new Post(postNumber, userName, threadTitle, threadId, date, postText, img);
                    posts.add(post);
                }
                
            }catch (Exception e) {
                e.printStackTrace();
            }
            
            //
            //投稿取得
            //
            String postsSql = "SELECT post_number, user_name, thread_title, posts.thread_id, posts.date, post, img FROM posts "
                    + "LEFT JOIN users ON posts.user_id = users.user_id LEFT JOIN threads ON posts.thread_id = threads.thread_id "
                    + "WHERE threads.thread_id = ? ORDER BY date OFFSET ? ROWS FETCH NEXT 19 ROWS ONLY";
            
            try(PreparedStatement statement = connection.prepareStatement(postsSql);){
                statement.setString(1, id);

                if (page == null || page.equals("0")) {
                    statement.setInt(2, 1);
                }else {
                    statement.setInt(2, Integer.parseInt(page) * 20);
                }
                
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int postNumber = resultSet.getInt("post_number");
                    String userName = resultSet.getString("user_name");
                    String threadTitle = resultSet.getString("thread_title");
                    int threadId = resultSet.getInt("thread_id");
                    Date date = resultSet.getTimestamp("date");
                    String postText = resultSet.getString("post");
                    String img = resultSet.getString("img");
                    Post post = new Post(postNumber, userName, threadTitle, threadId, date, postText, img);
                    posts.add(post);
                }
                
            }catch (Exception e) {
                e.printStackTrace();
            }
            
            //
            //投稿総数の取得
            //
            String countPostSql = "SELECT COUNT(thread_id) AS count_posts FROM posts WHERE thread_id = ?";
            int countPosts = 0;
            
            try(PreparedStatement statement = connection.prepareStatement(countPostSql);){
                
                statement.setString(1, id);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    countPosts = resultSet.getInt("count_posts");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            request.setAttribute("posts", posts);
            request.setAttribute("countPosts", countPosts);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/bbs.jsp");
        dispatcher.forward(request, response);
    }
    

}