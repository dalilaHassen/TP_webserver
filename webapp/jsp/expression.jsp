<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Expression</title>
</head>
<body>
    <h2>JSP expression</h2>
    <p>Random number (0 to 1): <%= Math.random() %></p>
    <p>Random number between 1 and 100: <%= (int) (Math.random() * 100) + 1 %></p>
    <p>Current date: <%= new java.util.Date() %></p>
    <p><a href="expression.jsp">Reload (new number)</a></p>
</body>
</html>
