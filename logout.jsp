<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト</title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
main {
    background-image: url(../../CSS/img/aruku.gif);
    background-repeat: no-repeat;
    background-size: 300px;
    background-position: bottom right;
    height: 500px;
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
        <span><img src="../../CSS/img/ウィンナ.png"><a href="../top">トップに戻る</a></span>
        <form action="login" method="get">
            <input type="submit" name="submit" value="ログイン">
            <input type="submit" name="submit" value="会員登録">
        </form>
    </nav>
</header>
<main>
<div class="rgba">
<h1>ログアウトしました</h1>
<form action="menu" method="get">
    <input type="submit" value="スレッド一覧" class="button">
</form>
</div>
</main>
<footer>
</footer>
</body>
</html>