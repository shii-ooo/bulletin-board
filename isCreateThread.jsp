<%@page import="java.awt.font.ImageGraphicAttribute"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板作成 確認画面</title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
main {
    height: 800px;
}
h1 {
    margin-top: 57px;
    margin-bottom: 42px;
}
.thread {
    display: flex;
    flex-direction: column;
    align-items: center;
}
.button {
    height: 40px;
}
.posts {
    display: flex;
    flex-direction: column;
    width: 400px;
}
</style>
</head>
<header>
<%  Boolean login = (Boolean)session.getAttribute("login"); %>
    <nav>
        <span><img src="../../../CSS/img/ウィンナ.png"><a href="../top">トップに戻る</a></span>
<% String id = (String)session.getAttribute("id");%>
        <span class="login">
        <p>ID:<%= id %></p>
        <a href="../logout">ログアウト</a>
        </span>
    </nav>
</header>
<body>
<main>
<% 
String threadTitle = (String)request.getAttribute("threadTitle");
String categroyName = (String)request.getAttribute("categoryName");
String post = (String)request.getAttribute("post");
String img = (String)request.getAttribute("img");
%>
<h1>新しいスレッドの作成</h1>
<p>こちらで作成しますか？</p>
<div class="thread">
<ul>
    <li>スレッドタイトル:<%= threadTitle %></li>
    <li>カテゴリー:<%= categroyName %></li>
</ul>
<div class="posts">
    <p>投稿</p>
<% if(img != null) {%>
    <p class="post"><%= post %></p>
    <p class="img"><img src="<%= request.getContextPath() %>/img/<%= img %>"></p>
 <%}else { %>
    <p class="post"><%= post %></p>
 <%} %>
 </div>
<form action="is_create_thread" method="post">
    <input type="hidden" name="threadTitle" value="<%= threadTitle %>">
    <input type="hidden" name="categoryName" value="<%= categroyName %>">
    <input type="hidden" name="post" value="<%= post %>">
<% if(img != null){ %>
    <input type="hidden" name="img" value="<%= img %>">
<% } %>
    <input type="submit"  name="submit" value="作成" class="button">
    <input type="submit" name="submit" value="キャンセル" class="button">
</form>
</div>
</main>
<footer>
<form action="../menu" method="get">
    <input type="submit" value="一覧へ戻る" class="backToTop">
</form>
</footer>
</body>
</html>