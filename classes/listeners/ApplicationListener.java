package listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("appName", "MP2 Web Application");
        System.out.println("[ApplicationListener] Application started: " + context.getContextPath());
        System.out.println("[ApplicationListener] appName = " + context.getAttribute("appName"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[ApplicationListener] Application stopped: "
                + sce.getServletContext().getAttribute("appName"));
    }
}
