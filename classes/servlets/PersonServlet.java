package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PersonServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final int ONE_WEEK = 7 * 24 * 60 * 60;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String sex       = request.getParameter("sex");

        // Cookies must be added BEFORE any content is written
        addCookie(request, response, "firstName", firstName);
        addCookie(request, response, "lastName",  lastName);
        addCookie(request, response, "birthDay",  birthDate);
        addCookie(request, response, "sex",       sex);

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><meta charset=\"UTF-8\"><title>Result</title></head>");
            out.println("<body>");
            out.println("<h2>Received information</h2>");
            out.println("<table border=\"1\" cellpadding=\"6\">");
            out.println("<tr><th>First name</th><th>Last name</th><th>Birth date</th><th>Sex</th></tr>");
            out.println("<tr><td>" + escape(firstName) + "</td><td>" + escape(lastName)
                      + "</td><td>" + escape(birthDate) + "</td><td>" + escape(sex) + "</td></tr>");
            out.println("</table>");
            out.println("<p>Cookies created.</p>");
            out.println("<p><a href=\"ReadCookie\">Read the cookies</a></p>");
            out.println("<p><a href=\"form.html\">Back</a></p>");
            out.println("</body></html>");
        }
    }

    private void addCookie(HttpServletRequest request, HttpServletResponse response,
                           String name, String value) {
        if (value == null) value = "";
        Cookie cookie = new Cookie(name, URLEncoder.encode(value, StandardCharsets.UTF_8));
        cookie.setMaxAge(ONE_WEEK);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
