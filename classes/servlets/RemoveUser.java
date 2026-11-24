package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RemoveUser extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("user");   // triggers attributeRemoved()
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<p>Attribute <b>user</b> removed from the session.</p>"
                + "<p><a href=\"login.html\">Login again</a></p>");
    }
}
