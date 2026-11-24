package listeners;

import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class RequestAttributeListener implements ServletRequestAttributeListener {

    @Override
    public void attributeAdded(ServletRequestAttributeEvent event) {
        System.out.println("[RequestAttributeListener] ADDED    : "
                + event.getName() + " = " + event.getValue());
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent event) {
        Object newValue = event.getServletRequest().getAttribute(event.getName());
        System.out.println("[RequestAttributeListener] REPLACED : "
                + event.getName() + " : " + event.getValue() + " -> " + newValue);
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent event) {
        System.out.println("[RequestAttributeListener] REMOVED  : "
                + event.getName() + " (old value = " + event.getValue() + ")");
    }
}
