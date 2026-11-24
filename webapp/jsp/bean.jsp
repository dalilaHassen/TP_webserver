<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="person" class="isep.lab1.bean.Person" scope="request" />
<jsp:setProperty name="person" property="*" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP + JavaBean</title>
</head>
<body>
    <h2>Information saved in the bean Person</h2>
    <table border="1" cellpadding="6">
        <tr><th>First name</th><th>Last name</th><th>Birth day</th><th>Sex</th></tr>
        <tr>
            <td><jsp:getProperty name="person" property="firstName" /></td>
            <td><jsp:getProperty name="person" property="lastName" /></td>
            <td><jsp:getProperty name="person" property="birthDay" /></td>
            <td><jsp:getProperty name="person" property="sex" /></td>
        </tr>
    </table>
    <p><a href="../form-jsp.html">Back to the form</a></p>
</body>
</html>
