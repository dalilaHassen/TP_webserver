package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReadCookie extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String firstName = null, lastName = null, birthDay = null, sex = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                String value = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
                switch (c.getName()) {
                    case "firstName": firstName = value; break;
                    case "lastName":  lastName  = value; break;
                    case "birthDay":  birthDay  = value; break;
                    case "sex":       sex       = value; break;
                    default: break;
                }
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset=\"UTF-8\"><title>Cookies</title></head><body>");
            out.println("<h2>Cookies read</h2>");
            if (firstName == null && lastName == null && birthDay == null && sex == null) {
                out.println("<p>No cookie found. Submit the form first.</p>");
            } else {
                out.println("<table border=\"1\" cellpadding=\"6\">");
                out.println("<tr><th>Cookie</th><th>Value</th></tr>");
                out.println("<tr><td>firstName</td><td>" + escape(firstName) + "</td></tr>");
                out.println("<tr><td>lastName</td><td>"  + escape(lastName)  + "</td></tr>");
                out.println("<tr><td>birthDay</td><td>"  + escape(birthDay)  + "</td></tr>");
                out.println("<tr><td>sex</td><td>"       + escape(sex)       + "</td></tr>");
                out.println("</table>");
            }
            out.println("<p><a href=\"form.html\">Back to the form</a></p>");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private String escape(String s) {
        if (s == null) return "(not set)";
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
