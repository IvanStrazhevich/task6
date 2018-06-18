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
    <title>Missed File Page</title>
</head>
<body>
You forgot to choose file. You can fix it here:<hr>
<form action="UploadPage"
      method="post">
    <input type="submit" style="color: darkslateblue" value="Upload Page">
    <input type="hidden" name="action" value="UploadPage">
</form>
Or you may switch to: <hr>
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: darkslateblue" value="Welcome Page">
</form>
</body>
</html>
