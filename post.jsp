<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="practice_webapp.Post"
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>入力の確認</title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
main {
    height: 800px;
    padding: 0 8%;
}
.posts {
    text-align: left;
    margin: 150px auto 100px;
    padding: 13px 10px 0 10px;
}
.button {
    margin: 10px;
}
.img {
    margin: 2px;
    padding: 0px 25px 20px;
}
.button {
    height: 40px;
}
</style>
</head>
<body>
<header>
    <nav>
        <span><img src="../../../CSS/img/ウィンナ.png"><a href="../top">トップに戻る</a></span>
<%      String id = (String)session.getAttribute("id");%>
        <span class="login">
        <p>ID:<%= id %></p>
        <a href="../logout">ログアウト</a>
        </span>
    </nav>
</header>
<main>
<% 
Post post = (Post)request.getAttribute("post"); 
if(post != null) {%>
<div class="posts">
<p><%= post.getUserName() %>:<%= post.getDate() %></p>
    <p class="post"><%= post.getPost() %></p>
<% if(post.getImg() != null) {%>
    <p class="img"><img src="<%= request.getContextPath() %>/img/<%= post.getImg() %>"></p>
 <%} %>
 </div>
<p>この内容で投稿しますか?</p>
<form action="is_post" method="get">
    <input type="hidden" name="userId" value="<%= id %>">
    <input type="hidden" name="threadId" value="<%= post.getThreadId() %>">
    <input type="hidden" name="date" value="<%= post.getDate() %>">
    <input type="hidden" name="post" value="<%= post.getPost() %>">
    <input type="hidden" name="img" value="<%= post.getImg() %>">
    <input type="submit" name="submit" value="投稿" class="button">
    <input type="submit" name="submit" value="キャンセル" class="button">
</form>
<% } else {%>
    <p><%= (String)request.getAttribute("errMsg") %></p>
    <a href="../bbs?id=<%= (String)request.getAttribute("threadId")%>">戻る</a>
<% }%>
</main>
<footer>
</footer>
</body>
</html>