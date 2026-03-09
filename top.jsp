<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>トップページ</title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
body {
    background-color: #e3f6f5;
}
main {
     background-image: url(../CSS/img/odorukome.gif);
     background-repeat: no-repeat;
     background-size: 350px;
     background-position: center;
}
.rgba {
    background: rgba(255,255,255,0.8);
    border-radius: 20px;
    margin: 0 45px;
    border: #272343 4px solid;
}
h1 {
    padding: 70px;
    margin: 0;
    letter-spacing: 10px;
}
.button {
    margin: 23px;
    height: 60px;
    width: 200px;
    letter-spacing: 6px;
}
 dl{
    margin: 40px;
}
</style>
</head>
<body>
<header>
</header>
<main>
<div class="rgba">
<h1>もぐもぐ掲示板</h1>
<p>今日の晩御飯の献立やおすすめのコンビニ飯、穴場のレストランまで!!<br>
グルメ情報の掲示板です!</p>
<dl>
    <dt>使い方&注意</dt>
    <dd>閲覧は匿名で行えます</dd>
    <dd>書き込み、スレッドの作成には会員登録が必要です</dd>
    <dd>その際IDが表示されます</dd>
    <dd>誹謗中傷等は禁止です!!</dd>
</dl>
<form action="admin/menu" method="get">
    <input type="submit" value="スレッド一覧" class="button">
</form>
</div>
</main>
<footer>
</footer>
</body>
</html>