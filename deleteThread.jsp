<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板を削除 確認画面</title>
<style type="text/css">
<%@ include file="../../../CSS/practice_webapp.css" %>
main {
    height: 600px;
}
h1 {
    margin-top: 100px;
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
    </nav>
</header>
<main>
<% 
String id = (String)request.getAttribute("id");
String title = (String)request.getAttribute("title");
String category = (String)request.getAttribute("category");
%>
<h1>掲示板を削除</h1>
<p>こちらを削除しますか?</p>
<div>
<ul>
    <li>掲示板:<%= title %></li>
    <li>カテゴリー:<%= category %></li>
</ul>
<form action="is_delete_thread" method="post">
    <input type="hidden" name="id" value="<%= id %>">
    <input type="submit"  name="submit" value="削除" class="button">
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