package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AttributeTest extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext context = getServletContext();

        // Application scope
        context.setAttribute("testApp", "value1");   // ADDED
        context.setAttribute("testApp", "value2");   // REPLACED
        context.removeAttribute("testApp");          // REMOVED

        // Request scope
        request.setAttribute("testReq", "A");        // ADDED
        request.setAttribute("testReq", "B");        // REPLACED
        request.removeAttribute("testReq");          // REMOVED

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><body>");
            out.println("<h2>Attribute test done</h2>");
            out.println("<p>Look at the Tomcat console.</p>");
            out.println("</body></html>");
        }
    }
}
