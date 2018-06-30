<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<fmt:setLocale value="${be_BY}" scope="session"/>
<fmt:setBundle basename="message" var="rb"/>
<html lang="be">
<head>
    <title><fmt:message key="label.button.RegisterPage" bundle="${rb}"/>
        <hr>
    </title>
</head>
<style type="text/css">
    body {
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #38b3cd;
        color: #616161;
    }
</style>
<body>
<hr>
<fmt:message key="label.button.RegisterPage" bundle="${rb}"/>
${userExist}
${needLogin}
<form action="RegisterUser" method=post>
    <p><strong><fmt:message key="message.enterLogin" bundle="${rb}"/> </strong>
        <input type="text" name="login" size="15">
    <p>
    <p><strong><fmt:message key="message.enterPassword" bundle="${rb}"/> </strong>
        <input type="password" name="password" size="15">
    <p>
    <p>
        <input type="hidden" size="15" name="action" value="RegisterUser">
        <input type="submit" value="<fmt:message key="label.button.Register" bundle="${rb}"/>">
        <input type="reset" value="<fmt:message key="label.button.Reset" bundle="${rb}"/>">
</form>
<hr>
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: #616161" value="<fmt:message key="label.button.WelcomePage" bundle="${rb}"/>">
</form>
</body>
</html>
