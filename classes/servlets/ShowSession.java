package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowSession extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Optional: ShowSession?logout=true invalidates the session
        if ("true".equals(request.getParameter("logout"))) {
            HttpSession s = request.getSession(false);
            if (s != null) {
                s.invalidate();
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<p>Session invalidated.</p>"
                    + "<p><a href=\"ShowSession\">Back to ShowSession</a></p>");
            return;
        }

        HttpSession session = request.getSession(true);

        Integer visits = (Integer) session.getAttribute("visits");
        boolean firstTime = (visits == null);
        visits = firstTime ? 1 : visits + 1;
        session.setAttribute("visits", visits);

        display(response, session, firstTime, visits);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);

        String fullName = request.getParameter("fullName");
        String birth = request.getParameter("birthDate");   // yyyy-MM-dd (input type="date")

        try {
            LocalDate date = LocalDate.parse(birth);
            if (fullName != null && !fullName.trim().isEmpty()) {
                session.setAttribute("fullName", fullName.trim());
                session.setAttribute("birthDate", date);
            }
        } catch (DateTimeParseException | NullPointerException e) {
            // invalid date: nothing stored, the form is shown again
        }

        Integer visits = (Integer) session.getAttribute("visits");
        if (visits == null) {
            visits = 1;
            session.setAttribute("visits", visits);
        }
        // Submitting the form is not a new visit: the counter is not incremented
        display(response, session, visits == 1, visits);
    }

    private void display(HttpServletResponse response, HttpSession session,
                         boolean firstTime, int visits) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        String fullName = (String) session.getAttribute("fullName");
        LocalDate birth = (LocalDate) session.getAttribute("birthDate");

        // Value maintained by ActiveSessionListener
        Object active = session.getServletContext().getAttribute("activeSessions");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset=\"UTF-8\"><title>ShowSession</title></head><body>");

            out.println(firstTime ? "<h2>Welcome on my site</h2>" : "<h2>Welcome back</h2>");
            out.println("<p>Number of visits: <b>" + visits + "</b></p>");
            out.println("<p>Currently active sessions: <b>" + (active == null ? 0 : active) + "</b></p>");

            if (fullName == null || birth == null) {
                out.println("<form action=\"ShowSession\" method=\"post\">");
                out.println("Full name: <input type=\"text\" name=\"fullName\" required><br><br>");
                out.println("Date of birth: <input type=\"date\" name=\"birthDate\" required><br><br>");
                out.println("<input type=\"submit\" value=\"Send\">");
                out.println("</form>");
            } else {
                long days = daysToBirthday(birth);
                if (days == 0) {
                    out.println("<p>Hi, " + escape(fullName) + ". Happy birthday today!</p>");
                } else {
                    out.println("<p>Hi, " + escape(fullName) + ". There are " + days
                            + " days to your birthday.</p>");
                }
            }
            out.println("<p>Session ID: " + session.getId() + "</p>");
            out.println("<p><a href=\"ShowSession?logout=true\">Invalidate my session</a></p>");
            out.println("</body></html>");
        }
    }

    private long daysToBirthday(LocalDate birth) {
        LocalDate today = LocalDate.now();
        MonthDay md = MonthDay.from(birth);
        LocalDate next = md.atYear(today.getYear());
        if (next.isBefore(today)) {
            next = md.atYear(today.getYear() + 1);
        }
        return ChronoUnit.DAYS.between(today, next);
    }

    private String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
