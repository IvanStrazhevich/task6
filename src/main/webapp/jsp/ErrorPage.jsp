<%--
  Created by IntelliJ IDEA.
  User: ivanstrazhevich
  Date: 14/6/18
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
Smth went wrong <hr>
You may switch to: <hr>
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: darkslateblue" value="Welcome Page">
</form>
</body>
</html>
