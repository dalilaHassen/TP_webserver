package filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

// Registered in web.xml (no @WebFilter): one registration method only
public class LoggingFilter implements Filter {

    private String logPrefix;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logPrefix = filterConfig.getInitParameter("logPrefix");
        if (logPrefix == null) {
            logPrefix = "[LoggingFilter]";
        }
        System.out.println(logPrefix + " Filter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        StringBuffer url = httpRequest.getRequestURL();
        String query = httpRequest.getQueryString();
        if (query != null) {
            url.append('?').append(query);
        }

        long start = System.nanoTime();
        System.out.println(logPrefix + " BEFORE chain.doFilter(): "
                + httpRequest.getMethod() + " " + url);

        chain.doFilter(request, response);

        double durationMs = (System.nanoTime() - start) / 1_000_000.0;
        System.out.println(logPrefix + " AFTER chain.doFilter(): request processed in "
                + String.format("%.3f", durationMs) + " ms");
    }

    @Override
    public void destroy() {
        System.out.println(logPrefix + " Filter destroyed.");
    }
}
