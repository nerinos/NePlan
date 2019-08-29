package NePlanProject;

import java.util.Date;
import java.util.EventObject;

public class DialogEvent extends EventObject {
    private String name;
    private String description;
    private Date date;

    public DialogEvent(Object source) {
        super(source);
    }

    public DialogEvent(Object source, String name, String description, Date date) {

        super(source);

        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
