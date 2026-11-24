package listeners;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("[SessionAttributeListener] ADDED    : "
                + event.getName() + " = " + event.getValue()
                + " | session " + event.getSession().getId());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        // getValue() returns the OLD value; the new one is read from the session
        Object oldValue = event.getValue();
        Object newValue = event.getSession().getAttribute(event.getName());
        System.out.println("[SessionAttributeListener] REPLACED : "
                + event.getName() + " : " + oldValue + " -> " + newValue
                + " | session " + event.getSession().getId());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("[SessionAttributeListener] REMOVED  : "
                + event.getName() + " (old value = " + event.getValue() + ")"
                + " | session " + event.getSession().getId());
    }
}
