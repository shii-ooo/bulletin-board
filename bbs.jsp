<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="practice_webapp.Post"
    import="java.util.ArrayList"
    import="java.util.Date"
    %>
    <% ArrayList<Post> posts = (ArrayList) request.getAttribute("posts"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= posts.get(0).getThreadTitle() %></title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
main {
    background-color: #fffffe;
    padding: 0 8%;
    border: #2d334a 4px solid;
    border-radius: 25px;
}
.page {
    display: block;
    font-weight: bolder;
    margin: 25px 0;
}
p, .page {
    text-align: left;
}
p {
    margin: 16px auto 0;
    padding: 13px 10px 0 10px;
}
.post {
    margin: 0 auto 0 30px;
    padding: 0 0px 20px;
}
h1 {
    padding: 100px 0 50px;
}
body {
    background-color: #e3f6f5;
}

.button {
    width: 130px;
    height: 35px;
    margin-top: 18px;
    letter-spacing: 3px;
}
.main {
    margin: 60px;
}
.img {
    margin: 2px;
    padding: 0px 25px 20px;
}
.submit {
    margin-top: 25px;
}
</style>
</head>
<body>
<header>
<%  
Boolean login = (Boolean)session.getAttribute("login");
Boolean admin = (Boolean)session.getAttribute("admin");
%>
    <nav>
        <span><img src="../../CSS/img/ウィンナ.png"><a href="../top">トップに戻る</a></span>
<%  if(login != null){ 
         String id = (String)session.getAttribute("id");%>
        <span class="login">
        <p>ID:<%= id %></p>
        <a href="logout">ログアウト</a>
        </span>
<%   }else { %>
        <form action="login" method="get">
            <input type="submit" name="submit" value="ログイン">
            <input type="submit" name="submit" value="会員登録">
        </form>
<%   } %>
    </nav>
</header>
<main>
<h1><%= posts.get(0).getThreadTitle() %></h1>
<% int pageNumber = request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page"));%>
<% for(int i = 0; i < posts.size(); i++){ 
        if(i == 0){%>
            <div class="posts">
            <p>1:<%= posts.get(i).getUserName() %>:<%= posts.get(i).getDate() %></p>
            <p class="post"><%= posts.get(i).getPost() %></p>
            <% if(posts.get(i).getImg() != null) {%>
                <p class="img"><img src="<%= request.getContextPath() %>/img/<%= posts.get(i).getImg() %>"></p>
            <%} %>
            </div>
<%      } else{%>
            <div class="posts">
            <p><%= (pageNumber * 19 +i +1) %>:<%= posts.get(i).getUserName() %>:<%= posts.get(i).getDate() %></p>
            <p class="post"><%= posts.get(i).getPost() %></p>
            <% if(posts.get(i).getImg() != null) {%>
                <p class="img"><img src="<%= request.getContextPath() %>/img/<%= posts.get(i).getImg() %>"></p>
            <%} %>
            </div>
    <%  }
        if(admin != null && admin){ %>
            <form action="create/delete_post" method="post" class="delete">
                <input type="hidden" name="postNumber" value="<%= posts.get(i).getPostNumber() %>">
                <input type="hidden" name="threadId" value="<%= posts.get(i).getThreadId() %>">
                <input type="hidden" name="userName" value="<%= posts.get(i).getUserName() %>">
                <input type="hidden" name="date" value="<%= posts.get(i).getDate() %>">
                <input type="hidden" name="post" value="<%= posts.get(i).getPost() %>">
                <% if(posts.get(i).getImg() != null) {%>
                <input type="hidden" name="img" value="<%= posts.get(i).getImg() %>">
            <%} %>
                <input type="submit" name="submit" value="削除" class="button">
            </form>
<%      }
   }%>
<% int countPosts = (int) request.getAttribute("countPosts");
    if(countPosts > 20){ %>
        <span class="page">
<%      if(pageNumber == 0){%>
            <a href="bbs?id=<%=posts.get(0).getThreadId()%>&page=<%= (pageNumber + 1) %>">次のページ</a>
            </span>
<%      }else if(pageNumber == countPosts/20){ %>
            <a href="bbs?id=<%=posts.get(0).getThreadId()%>&page=<%= (pageNumber - 1) %>">前のページ</a>
            </span>
<%      }else {%>
            <a href="bbs?id=<%=posts.get(0).getThreadId()%>&page=<%= (pageNumber - 1) %>">前のページ</a>
            <a href="bbs?id=<%=posts.get(0).getThreadId()%>&page=<%= (pageNumber + 1) %>">次のページ</a>
            </span>
            </
<%      } %>
<%  }
    if(login != null){ 
        String errPost =(String)session.getAttribute("errPost");
        String post = (String)session.getAttribute("post");
        if(errPost != null) out.print("<p class=\"errMsg\">" + errPost + "</p>");
    %>
        <form action="create/post" method="post" enctype="multipart/form-data" class="submit">
            <textarea name="post" <% 
                    if(errPost != null){
                        out.print(" class=\"error\" value=\"" + post + "\"");
                    }else {
                        if(post != null){
                            out.print(" class=\"input\" value=\"" + post + "\"");
                        }else {
                            out.print(" class=\"input\"");
                        }
                    }
        %>></textarea>
            <input type="file" name="img" class="file">
            <input type="hidden" name="threadId" value="<%= posts.get(0).getThreadId() %>">
            <input type="submit" value="投稿" class="button">
        </form>
<%  }else { %>
        <a href="login" class="submit">ログインして投稿する</a>
<%  }
    %>
    <div class="main"></div>
</main>
<footer>
<form action="menu" method="get">
    <input type="submit" value="一覧へ戻る" class="backToTop">
</form>
</footer>
</body>
</html>