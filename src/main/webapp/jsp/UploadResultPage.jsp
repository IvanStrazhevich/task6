<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Upload Result Page</title>
</head>
<body>

${result}<br>
${parser}<br>

<c:forEach items="${authors}" var="au">
    ${au.authorName}
</c:forEach><br>
from dao: <br>
<c:forEach items="${authorsdao}" var="au">
    ${au.authorName}
</c:forEach><br>
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: darkslateblue" value="Welcome Page">
</form>
</body>
</html>
