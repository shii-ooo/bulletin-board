<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>削除エラー</title>
<style type="text/css">
<%@ include file="../../../CSS/practice_webapp.css" %>
main {
    height: 600px;
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
<% String errMsg = (String)request.getAttribute("errMsg"); %>
<p><%= errMsg %></p>
</main>
<footer>
<form action="../menu" method="get">
    <input type="submit" value="一覧へ戻る" class="backToTop">
</form>
</footer>
</body>
</html>