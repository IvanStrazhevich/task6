<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<fmt:setLocale value="${be-BY}" scope="session"/>
<fmt:setBundle basename="message" var="rb"/>
<html>
<head>
    <title>Error Page</title>
</head>
<style type="text/css">
    body {
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #38b3cd;
    }
</style>
<body>
<fmt:message key="message.smthwentwrong" bundle="${rb}"/> <hr>
 <hr>
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: #616161" value="<fmt:message key="label.button.WelcomePage" bundle="${rb}"/>">
</form>
</body>
</html>
