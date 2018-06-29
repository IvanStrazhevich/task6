<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<fmt:setLocale value="${be-BY}" scope="session"/>
<fmt:setBundle basename="message" var="rb"/>
<html>
<head>
    <title><fmt:message key="label.button.ErrorPage" bundle="${rb}"/></title>
</head>
<style type="text/css">
    TABLE {
        border-collapse: collapse;
    }

    TD, TH {
        padding: 3px;
        border: 1px solid rgba(30, 66, 84, 0.97);
    }

    TH {
        background: #38b3cd; /* Цвет фона */
    }

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

<fmt:message key="message.forgotfile" bundle="${rb}"/><hr>
<form action="UploadPage"
      method="post">
    <input type="submit" style="color: #616161" value="<fmt:message key="label.button.UploadPage" bundle="${rb}"/>">
    <input type="hidden" name="action" value="UploadPage">
</form>
<fmt:message key="message.youcanswitch" bundle="${rb}"/> <hr>
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: #616161" value="<fmt:message key="label.button.WelcomePage" bundle="${rb}"/>">
</form>
</body>
</html>
