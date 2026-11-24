package listeners;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationAttributeListener implements ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("[ApplicationAttributeListener] ADDED    : "
                + event.getName() + " = " + event.getValue());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        Object newValue = event.getServletContext().getAttribute(event.getName());
        System.out.println("[ApplicationAttributeListener] REPLACED : "
                + event.getName() + " : " + event.getValue() + " -> " + newValue);
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        System.out.println("[ApplicationAttributeListener] REMOVED  : "
                + event.getName() + " (old value = " + event.getValue() + ")");
    }
}
