<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="practice_webapp.ThreadTitle"
    import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー</title>
<style type="text/css">
<%@ include file="../../CSS/practice_webapp.css" %>
main {
    background-image: url(../../CSS/img/onigiri.png);
    background-repeat: no-repeat;
    background-size: 500px;
    background-position: bottom right;
    text-align: left;
    margin-bottom: 100px;
    height: 100%;
}
.rgba {
    background: rgba(255,255,255,0.8);
    min-height: 1500px;
}
.notFind, .find {
    text-align: center;
    margin: 90px 20px 30px;
}
.find input, select {
    height: 30px;
    padding: 0 6px;
    border: #272344 3px solid;
    border-radius: 5px;
    font-weight: bolder;
    letter-spacing: 3px;
}
a {
    font-weight: bolder;
}
ol p {
    display: inline;
}
.thread p {
    width: 200px;
    margin-left: 6px;
}
.thread {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-left: 58px;
    margin-block: 15px;
}
.mainBottom {
    margin: 30px;
}
.page {
    margin: 50px;
}
.login a {
    font-weight: normal;
    font-size: 14px;
}
</style>
</head>
<body>
<header>
<%
Boolean login = (Boolean)session.getAttribute("login");
Boolean admin = (Boolean)session.getAttribute("admin");
%>
    <nav>
        <span><img src="../../CSS/img/ウィンナ.png"><a href="../top" class="top">トップに戻る</a></span>
<%  if(login != null){ 
         String id = (String)session.getAttribute("id");%>
        <span class="login">
        <p>ID:<%= id %></p>
        <a href="logout">ログアウト</a>
        <a href="create/create_thread">掲示板を作る</a>
        </span>
<%   }else { %>
        <form action="login" method="get">
            <input type="submit" name="submit" value="ログイン">
            <input type="submit" name="submit" value="会員登録">
        </form>
<%   } %>
    </nav>
</header>
<main>
<div class="rgba">
<%  String errFind =(String)request.getAttribute("errFind");%>
<!-- 検索 -->
    <form action="find" method="get" class="find">
        <select name="category">
            <option value="days">日常</option>
            <option value="dinner">ディナー</option>
            <option value="lanch">ランチ</option>
            <option value="sapporo">札幌</option>
            <option value="hokkaido">北海道</option>
        </select>
        <input type="text" name="find" <%
                if(errFind != null){
                    out.print(" class=\"error\"");
                }
        %>> <input type="submit" value="検索">
        </form>
        <%
        if(errFind != null) out.print("<p class=\"errMsg\">" + errFind + "</p>");
        ArrayList<ThreadTitle> threads = (ArrayList) request.getAttribute("threads");
        int pageNumber = request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page"));
        %>
    
<!-- 掲示板一覧 -->
<%      if(threads != null && threads.size() != 0){
          %><ol>
<%          for (int i = 0; i < threads.size(); i++) {
                if(pageNumber == 0){
                    if (i == 3) {
                        out.print("</ol>");
                    }
                    if (i < 3) {%>
                        <li><a href="bbs?id=<%=threads.get(i).getThreadId()%>"><%=threads.get(i).getTitle()%></a>
                        <p><%=threads.get(i).getCategoryName()%></p></li>
         <%         } else { %>
                        <span class="thread"><a href="bbs?id=<%=threads.get(i).getThreadId()%>"><%=threads.get(i).getTitle()%></a>
                        <p><%= threads.get(i).getCategoryName()%></p></span>
          <%        }
                }else { %>
                        <span class="thread"><a href="bbs?id=<%=threads.get(i).getThreadId()%>"><%=threads.get(i).getTitle()%></a>
                        <p><%= threads.get(i).getCategoryName()%></p></span>
              <%}
                if(admin != null){%>
                <form action="create/delete_thread" method="post" class="delete">
                    <input type="hidden" name="threadId" value="<%= threads.get(i).getThreadId() %>">
                    <input type="hidden" name="threadTitle" value="<%= threads.get(i).getTitle() %>">
                    <input type="hidden" name="threadCategory" value="<%= threads.get(i).getCategoryName() %>">
                    <input type="submit" value="削除" class="button">
                </form>
<%                  }
            } %>
            <div class="page"></div>
            <%
            int countPosts = (int) request.getAttribute("countPosts");
            if(countPosts > 20){
                if(pageNumber == 0){%>
                    <a href="menu?page=<%= (pageNumber + 1) %>">次のページ</a>
<%              }else if(pageNumber == countPosts/20){ %>
                    <a href="menu?page=<%= (pageNumber - 1) %>">前のページ</a>
<%              }else {%>
                    <a href="menu?page=<%= (pageNumber - 1) %>">前のページ</a>
                    <a href="menu?page=<%= (pageNumber + 1) %>">次のページ</a>
<%              }
            }%>
<%      }else {%>
            <p class="notFind">検索結果がありません</p>
<%      }%>
<span class="mainBottom"></span>
</div>
</main>
<footer>
</footer>
</body>
</html>