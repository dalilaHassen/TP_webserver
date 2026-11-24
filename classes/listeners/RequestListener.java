package listeners;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;

// Registered in web.xml with <listener> (no @WebListener): one registration method only
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            System.out.println("[RequestListener] requestInitialized: "
                    + req.getMethod() + " " + req.getRequestURI());
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            System.out.println("[RequestListener] requestDestroyed: "
                    + req.getMethod() + " " + req.getRequestURI());
        }
    }
}
