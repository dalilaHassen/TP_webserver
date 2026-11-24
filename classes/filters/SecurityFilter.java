package filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[SecurityFilter] Filter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // false = do NOT create a session if none exists
        HttpSession session = httpRequest.getSession(false);
        boolean connected = (session != null && session.getAttribute("user") != null);

        if (connected) {
            System.out.println("[SecurityFilter] BEFORE chain.doFilter(): access granted for "
                    + session.getAttribute("user"));

            chain.doFilter(request, response);

            System.out.println("[SecurityFilter] AFTER chain.doFilter(): servlet finished.");
        } else {
            System.out.println("[SecurityFilter] Access refused: redirecting to login.html "
                    + "(chain.doFilter() NOT called)");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
        }
    }

    @Override
    public void destroy() {
        System.out.println("[SecurityFilter] Filter destroyed.");
    }
}
