package listeners;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class ActiveSessionListener implements HttpSessionListener {

    // Thread-safe counter: several sessions can be created/destroyed at the same time
    private final AtomicInteger activeSessions = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int count = activeSessions.incrementAndGet();
        ServletContext context = se.getSession().getServletContext();
        context.setAttribute("activeSessions", count);
        System.out.println("[ActiveSessionListener] Session created: "
                + se.getSession().getId() + " | active sessions = " + count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int count = activeSessions.decrementAndGet();
        ServletContext context = se.getSession().getServletContext();
        context.setAttribute("activeSessions", count);
        System.out.println("[ActiveSessionListener] Session destroyed: "
                + se.getSession().getId() + " | active sessions = " + count);
    }
}
