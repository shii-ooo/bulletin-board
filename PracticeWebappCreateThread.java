package practice_webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class PracticeWebappCreateThread
 */
@MultipartConfig
@WebServlet("/practice_webapp/admin/create/create_thread")
public class PracticeWebappCreateThread extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String threadId = (String) request.getAttribute("thread_id");
        if (threadId != null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                    + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                    + "password=SQLPassword1234";

            String sql = "DELETE threads WHERE thread_id = ?";

            try (Connection connection = DriverManager.getConnection(url);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, threadId);

                statement.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/createThread.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String threadTitle = request.getParameter("thread_title");
        String category = request.getParameter("category");
        Part part = request.getPart("img");
        String post = request.getParameter("post");
        String categoryName = null;
        if (category.equals("days")) {
            categoryName = "日常";
        } else if (category.equals("dinner")) {
            categoryName = "ディナー";
        } else if (category.equals("lanch")) {
            categoryName = "ランチ";
        } else if (category.equals("sapporo")) {
            categoryName = "札幌";
        } else if (category.equals("hokkaido")) {
            categoryName = "北海道";
        }

        if (threadTitle == null || threadTitle.equals("")) {
            request.setAttribute("errTitle", "タイトルが未入力です");
            doGet(request, response);
        } else if (post == null || post.equals("")) {
            request.setAttribute("errPost", "本文を入力してください");
            doGet(request, response);
        } else {

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;" + "enxrypt=true;"
                    + "trustServerCertificate=true;" + "integratedSecurity=false;" + "user=sa;"
                    + "password=SQLPassword1234";

            String sql = "SELECT * FROM threads WHERE thread_title = ?";

            try (Connection connection = DriverManager.getConnection(url);
                    PreparedStatement statement = connection.prepareStatement(sql);) {

                statement.setString(1, threadTitle);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    request.setAttribute("errMsg", "重複したスレッド名が存在します スレッド名を変更してください");
                    doGet(request, response);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String imgName = part.getSubmittedFileName();
            if (imgName != null && !imgName.equals("")) {
                String imgPath = getServletContext().getRealPath("/img");
                part.write(imgPath + File.separator + imgName);
                request.setAttribute("img", imgName);
            }

            request.setAttribute("threadTitle", threadTitle);
            request.setAttribute("category", category);
            request.setAttribute("categoryName", categoryName);
            request.setAttribute("post", post);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/practice_webapp/isCreateThread.jsp");
            dispatcher.forward(request, response);
        }

    }

}