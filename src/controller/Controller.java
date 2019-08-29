package controller;

import NePlanProject.DialogEvent;
import model.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Controller {
    Database db = new Database();

    public EventClass getNearestEvent() {
        return db.getNearestEvent();
    }

    public List<EventClass> getEvents(Date date) {
        return db.getEvents(date);
    }

    public void addEvent(DialogEvent ev) {
        String name = ev.getName();
        String description = ev.getDescription();
        Date date = ev.getDate();
        EventClass event = new EventClass(date, name, description);
        db.addEvent(event);
    }

    public void removeEvent(String name, String time) {
        db.removeEvent(name, time);
    }

    public void saveToFile(File file) throws IOException {
        db.saveToFile(file);
    }

    public void loadFromFile(File file) throws IOException {
        db.loadFromFile(file);
    }

    public List<EventClass> getAllEvents() {
        return db.getAllEvents();
    }
}
