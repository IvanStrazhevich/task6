<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<fmt:setLocale value="${be_BY}" scope="session"/>
<fmt:setBundle basename="message" var="rb"/>
<html lang="be">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Upload Result Page</title>
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

${result}
${parser}<br>


<div>
    <h4>
        <table style="color: #616161">
            <tr>
                <th><fmt:message key="label.header.PostcardId" bundle="${rb}"/> </th>
                <th><fmt:message key="label.header.Theme" bundle="${rb}"/></th>
                <th><fmt:message key="label.header.CardType" bundle="${rb}"/></th>
                <th><fmt:message key="label.header.Sent" bundle="${rb}"/></th>
                <th><fmt:message key="label.header.Country" bundle="${rb}"/></th>
                <th><fmt:message key="label.header.Year" bundle="${rb}"/></th>
                <th><fmt:message key="label.header.Valuable" bundle="${rb}"/></th>
                <th><fmt:message key="label.header.AuthorName" bundle="${rb}"/></th>
            </tr>
            <c:forEach items="${postcards}" var="pc">
                <tr>
                    <td>${pc.postcardId}</td>
                    <td>${pc.theme}</td>
                    <td>${pc.cardType}</td>
                    <td>${pc.sent}</td>
                    <td>${pc.postcardCharachteristics.country}</td>
                    <td>${pc.postcardCharachteristics.year}</td>
                    <td>${pc.postcardCharachteristics.valuable}</td>
                    <td>${pc.postcardCharachteristics.author.authorName} ${pc.postcardCharachteristics.author.authorLastName}
                    </td>
                </tr>
            </c:forEach><br>

        </table>
    </h4>
</div>
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: #616161" value="<fmt:message key="label.button.WelcomePage" bundle="${rb}"/>">
</form>
</body>
</html>
