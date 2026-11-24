<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="person" type="lab.mvc.model.Person" scope="request" />
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Young person</title></head>
<body>
    <h2>Young person</h2>
    <table border="1" cellpadding="6">
        <tr><th>First name</th><th>Last name</th><th>Birth day</th><th>Sex</th></tr>
        <tr>
            <td><jsp:getProperty name="person" property="firstName" /></td>
            <td><jsp:getProperty name="person" property="lastName" /></td>
            <td><jsp:getProperty name="person" property="birthDay" /></td>
            <td><jsp:getProperty name="person" property="sex" /></td>
        </tr>
    </table>
    <p><a href="index.html">Back to the form</a></p>
</body>
</html>
