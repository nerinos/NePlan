package NePlanProject;

import java.util.EventListener;

public interface CalendarListener extends EventListener {
    void calEventOccured(CalendarEvent e);
}
