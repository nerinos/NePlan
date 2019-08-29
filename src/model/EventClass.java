package model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class EventClass implements Serializable {

    private String name;
    private Date date;
    private String description;
    private static int count = 0;
    private int id;

    public EventClass(Date date, String name, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.id = count;
        count++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Comparator<EventClass> compareDate = new Comparator<EventClass>() {
        @Override
        public int compare(EventClass o1, EventClass o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    };
}
