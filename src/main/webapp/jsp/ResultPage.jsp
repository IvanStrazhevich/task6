
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Result Page</title>
</head>
<body>
    ${result}
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: darkslateblue" value="Welcome Page">
</form>
</body>
</html>
