<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板を作成</title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
main {
    height: 600px;
}
h1 {
    margin-top: 57px;
    margin-bottom: 42px;
}
.file {
    margin: 20px;
}
.input, .error {
    margin-bottom : 18px;
    margin-right: 30px;
}
.img {
    width: 60px;
    height: auto;
}
.title {
    display: flex;
    align-items: center;
    justify-content: center;
}
.button {
    height: 35px;
}
</style>
</head>
<body>
<header>
<%  Boolean login = (Boolean)session.getAttribute("login"); %>
    <nav>
        <span><img src="../../../CSS/img/ウィンナ.png"><a href="../top">トップに戻る</a></span>
<%String id = (String)session.getAttribute("id");%>
        <span class="login">
        <p>ID:<%= id %></p>
        <a href="../logout">ログアウト</a>
        </span>
    </nav>
</header>
<main>
<%
String errMsg =(String)request.getAttribute("errMsg");
String errTitle =(String)request.getAttribute("errTitle");
String errPost =(String)request.getAttribute("errPost");
String title = request.getParameter("thread_title");
%>
<span class="title"><h1>掲示板を作成</h1><img src="../../../CSS/img/anpan-1.gif" alt="anpan" class="img"></span>
<form action="create_thread" method="post" enctype="multipart/form-data" class="form">
    <span class="input">スレッドタイトル:
    <input type="text" name="thread_title" <% 
        if(errTitle != null){
            out.print(" class=\"error\" value=\"" + title + "\"");
        }else {
            if(title != null){
                out.print(" class=\"input\" value=\"" + title + "\"");
            }else {
                out.print(" class=\"input\"");
            }
        }
        %>></span>
    <span class="input">カテゴリー:
    <select name="category">
        <option value="days">日常</option>
        <option value="dinner">ディナー</option>
        <option value="lanch">ランチ</option>
        <option value="sapporo">札幌</option>
        <option value="hokkaido">北海道</option>
    </select></span>
    <p>最初の投稿</p>
    <textarea name="post" <% 
        if(errPost != null){
            out.print(" class=\"error\"");
        }
        %>></textarea>
    <input type="file" name="img" class="file"><br>
<%
if(errMsg != null) out.print("<p class=\"errMsg\">" + errMsg + "</p>");
if(errTitle != null) out.print("<p class=\"errMsg\">" + errTitle + "</p>");
if(errPost != null) out.print("<p class=\"errMsg\">" + errPost + "</p>");
%>
    <input type="submit" value="作成" class="button">
</form>
</main>
<footer>
<form action="../menu" method="get">
    <input type="submit" value="一覧へ戻る" class="backToTop">
</form>
</footer>
</body>
</html>