<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿を削除</title>
<style type="text/css">
<%@ include file="../../../CSS/practice_webapp.css" %>
main {
    height: 600px;
}
.posts {
    margin-top: 100px;
}
.button {
    height: 40px;
}
</style>
</head>
</head>
<body>
<header>
    <nav>
        <span><img src="../../../CSS/img/ウィンナ.png"><a href="../top">トップに戻る</a></span>
    </nav>
</header>
<main>
<% 
String postNumber = (String)request.getAttribute("postNumber");
String threadId = (String)request.getAttribute("threadId");
String userName = (String)request.getAttribute("userName");
String date = (String)request.getAttribute("date");
String post = (String)request.getAttribute("post");
String img = (String)request.getAttribute("img");
%>
<div class="posts">
<p><%= userName %>:<%= date %></p>
    <p class="post"><%= post %></p>
<% if(img != null) {%>
    <p class="img"><img src="<%= request.getContextPath() %>/img/<%= img %>"></p>
 <%} %>
 </div>
<p>この内容で削除しますか?</p>
<form action="is_delete_post" method="post">
    <input type="hidden" name="postNumber" value="<%= postNumber %>">
    <input type="hidden" name="threadId" value="<%= threadId %>">
    <input type="submit" name="submit" value="削除" class="button">
    <input type="submit" name="submit" value="キャンセル" class="button">
</form>
</main>
<footer>
<form action="../menu" method="get">
    <input type="submit" value="一覧へ戻る" class="backToTop">
</form>
</footer>
</body>
</html>