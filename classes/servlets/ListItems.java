package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ListItems extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);

        @SuppressWarnings("unchecked")
        ArrayList<String> items = (ArrayList<String>) session.getAttribute("items");
        if (items == null) {
            items = new ArrayList<>();
            session.setAttribute("items", items);
        }

        String newItem = request.getParameter("newItem");
        if (newItem != null && !newItem.trim().isEmpty()) {
            items.add(newItem.trim());
        }
        display(request, response, items);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        @SuppressWarnings("unchecked")
        ArrayList<String> items = (ArrayList<String>) session.getAttribute("items");
        if (items == null) {
            items = new ArrayList<>();
        }
        display(request, response, items);
    }

    private void display(HttpServletRequest request, HttpServletResponse response,
                         ArrayList<String> items) throws IOException {

        System.out.println("[ListItems] Servlet executing");
        response.setContentType("text/html;charset=UTF-8");

        // Extra: number of times each item was entered (insertion order kept)
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String item : items) {
            counts.merge(item, 1, Integer::sum);
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><meta charset=\"UTF-8\"><title>Items list</title></head>");
            out.println("<body>");
            out.println("<h2>Items entered by "
                    + escape((String) request.getSession().getAttribute("user")) + "</h2>");

            if (items.isEmpty()) {
                out.println("<p>The list is empty.</p>");
            } else {
                out.println("<h3>All items (" + items.size() + ")</h3>");
                out.println("<ul>");
                for (String item : items) {
                    out.println("<li>" + escape(item) + "</li>");
                }
                out.println("</ul>");

                out.println("<h3>Number of times each item was entered</h3>");
                out.println("<table border=\"1\" cellpadding=\"6\">");
                out.println("<tr><th>Item</th><th>Count</th></tr>");
                for (Map.Entry<String, Integer> e : counts.entrySet()) {
                    out.println("<tr><td>" + escape(e.getKey()) + "</td><td>"
                            + e.getValue() + "</td></tr>");
                }
                out.println("</table>");
            }

            out.println("<p><a href=\"items.html\">Add another item</a></p>");
            out.println("<p><a href=\"LogoutServlet\">Logout</a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
