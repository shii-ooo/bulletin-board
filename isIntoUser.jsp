<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員登録確認</title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
main {
    height: 600px;
}
div {
    display: flex;
    flex-direction: column;
    align-items: center;
}
.button {
    margin-right: 20px;
    height: 40px;
}
</style>
</head>
<body>
<header>
    <nav>
        <span><img src="../../CSS/img/ウィンナ.png"><a href="../top">トップに戻る</a></span>
        <form action="login" method="get">
            <input type="submit" name="submit" value="ログイン">
        </form>
    </nav>
</header>
<main>
<% 
String userName = (String)request.getAttribute("userName");
String id = (String)request.getAttribute("id");
String pw = (String)request.getAttribute("pw");
%>
<h1>会員登録</h1>
<p>こちらで登録しますか?</p>
<div>
<ul>
    <li>ユーザー名:<%= userName %></li>
    <li>ID:<%= id %></li>
    <li>パスワード:<%= pw %></li>
</ul>
<form action="is_into_user" method="post">
    <input type="hidden" name="userName" value="<%= userName %>">
    <input type="hidden" name="id" value="<%= id %>">
    <input type="hidden" name="pw" value="<%= pw %>">
    <input type="submit"  name="submit" value="登録" class="button"><input type="submit" name="submit" value="キャンセル" class="button">
</form>
</div>
</main>
<footer>
<form action="menu" method="get">
    <input type="submit" value="一覧へ戻る" class="backToTop">
</form>
</footer>
</body>
</html>