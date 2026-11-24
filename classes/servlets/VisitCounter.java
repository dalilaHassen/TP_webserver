package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VisitCounter extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int visits = 0;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("visits".equals(c.getName())) {
                    try {
                        visits = Integer.parseInt(c.getValue());
                    } catch (NumberFormatException e) {
                        visits = 0;
                    }
                }
            }
        }
        visits++;

        Cookie counter = new Cookie("visits", String.valueOf(visits));
        counter.setMaxAge(30 * 24 * 60 * 60);
        counter.setPath(request.getContextPath());
        response.addCookie(counter);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset=\"UTF-8\"><title>Visits</title></head><body>");
            if (visits == 1) {
                out.println("<h2>Welcome! This is your first visit.</h2>");
            } else {
                out.println("<h2>Welcome back!</h2>");
                out.println("<p>You have visited this page <b>" + visits + "</b> times.</p>");
            }
            out.println("<p>Refresh the page (F5) to increase the counter.</p>");
            out.println("<p><a href=\"form.html\">Back</a></p>");
            out.println("</body></html>");
        }
    }
}
