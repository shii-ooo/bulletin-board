<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員登録</title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
main {
    height: 600px;
    background-image: url(../../CSS/img/tamaoha.gif);
    background-repeat: no-repeat;
    background-size: 400px;
    background-position: bottom right;
}
.rgba {
    background: rgba(255,255,255,0.8);
    height: 600px;
}
.input, .error {
    margin-top: 20px;
    margin-right: 30px;
}
h1 {
    margin-top: 57px;
    margin-bottom: 42px;
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
<%
String errUserName = (String)request.getAttribute("errUserName");
String errId =(String)request.getAttribute("errId");
String errPw =(String)request.getAttribute("errPw");
String userName = request.getParameter("userName");
String id = request.getParameter("id");
String pw = request.getParameter("pw");
%>
<main>
<div class="rgba">
    <h1>会員登録</h1>
    <p>全て10文字以内で登録</p>
    <form action="into_user" method="post" class="form">
        <div class="inputContents">
        <span class="input">ユーザー名:
        <input type="text" name="userName" <% 
        if(errUserName != null){
            out.print(" class=\"error\" value=\"" + userName + "\"");
        }else {
            if(userName != null){
                out.print(" class=\"input\" value=\"" + userName + "\"");
            }else {
                out.print(" class=\"input\"");
            }
        }
        %>></span>
        <span class="input">ID:
        <input type="text" name="id" <% 
        if(errId != null){
            out.print(" class=\"error\" value=\"" + id + "\"");
        }else {
            if(id != null){
                out.print(" class=\"input\" value=\"" + id + "\"");
            }else {
                out.print(" class=\"input\"");
            }
        }
        %>></span>
        <span class="input">パスワード:
        <input type="password" name="pw" <% 
        if(errPw != null){
            out.print(" class=\"error\" value=\"" + pw + "\"");
        }else {
            if(pw != null){
                out.print(" class=\"input\" value=\"" + pw + "\"");
            }else {
                out.print(" class=\"input\"");
            }
        }
        %>></span><br>
        </div>
<%
        if(errUserName != null) out.print("<p class=\"errMsg\">" + errUserName + "</p>");
        if(errId != null) out.print("<p class=\"errMsg\">" + errId + "</p>");
        if(errPw != null) out.print("<p class=\"errMsg\">" + errPw + "</p>");
%>
        <input type="submit" value="決定" class="button">
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