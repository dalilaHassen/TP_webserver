<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Scriptlet</title>
</head>
<body>
    <h2>JSP scriptlet</h2>
    <%
        java.util.Date now = new java.util.Date();
        String remoteHost = request.getRemoteHost();
        String localAddr  = request.getLocalAddr();
        out.println("<p>Current time: " + now + "</p>");
        out.println("<p>Host (getRemoteHost, as asked): " + remoteHost + "</p>");
        out.println("<p>Server IP (getLocalAddr): " + localAddr + "</p>");
    %>
</body>
</html>
