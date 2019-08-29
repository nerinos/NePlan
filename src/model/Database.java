package model;

import java.io.*;
import java.util.*;

public class Database {
    private List<EventClass> events;

    public Database() {
        events = new ArrayList<EventClass>();
    }

    public Database(List<EventClass> events) {
        this.events = events;
    }

    public void addEvent(EventClass event) {
        events.add(event);
        Collections.sort(events, event.compareDate);
    }

    public void removeEvent(String name, String time) {
//        System.out.println(events.get(0).getDate());
        for (int i = 0; i < events.size(); i++) {
//            System.out.println(events.get(i).getDate().toString().substring(11, 16));
//            System.out.println(events.get(i).getName());
            if (events.get(i).getDate().toString().substring(11, 16).equals(time)
                    && events.get(i).getName().equals(name)) {
                events.remove(i);
                break;
            }
        }
    }

    public List<EventClass> getEvents(Date date) {
        List<EventClass> eventsDate = new ArrayList<EventClass>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getDate().getYear() == date.getYear() &&
                    events.get(i).getDate().getMonth() == date.getMonth() &&
                    events.get(i).getDate().getDate() == date.getDate()) {
                eventsDate.add(events.get(i));
            }
        }
        return Collections.unmodifiableList(eventsDate);
    }

    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        EventClass[] eventsN = events.toArray(new EventClass[events.size()]);

        oos.writeObject(eventsN);

        oos.close();
    }

    public void loadFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            EventClass[] eventsN = (EventClass[]) ois.readObject();

            events.clear();

            events.addAll(Arrays.asList(eventsN));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ois.close();
    }

    public EventClass getNearestEvent() {
        EventClass a = null;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getDate().after(new Date())) {
                a = events.get(i);
                break;
            }
        }
        return a;
    }

    public List<EventClass> getAllEvents() {
        return Collections.unmodifiableList(events);
    }
}
