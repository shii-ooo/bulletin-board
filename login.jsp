<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
main {
    height: 600px;
    background-image: url(../../CSS/img/ke-ki.gif);
    background-repeat: no-repeat;
    background-size: 350px;
    background-position: bottom right;
}
.rgba {
    background: rgba(255,255,255,0.8);
    height: 600px;
}
.form {
    display: flex;
    flex-direction: column;
    align-items: center;
}
.inputContents {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
}
.input, .error {
    margin-top: 20px;
    margin-right: 30px;
}
.button {
    margin-top: 40px;
    height: 40px;
}
.errMsg {
    margin-top: 25px;
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
            <input type="submit" name="submit" value="会員登録">
        </form>
    </nav>
</header>
<%
String errId =(String)request.getAttribute("errId");
String errPw =(String)request.getAttribute("errPw");
String id = request.getParameter("id");
String pw = request.getParameter("pw");
%>
<main>
<div class="rgba">
<h1>ログイン</h1>
<form action="login" method="post" class="form">
    <div class="inputContents">
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
        %>></span>
        </div>
<%
        if(errId != null) out.print("<p class=\"errMsg\">" + errId + "</p>");
        if(errPw != null) out.print("<p class=\"errMsg\">" + errPw + "</p>");
%>
    <input type="submit" value="ログイン" class="button">
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