package de.nordakademie.pdse.simulation.timeline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimeLine {
    private final List<IEvent> allEvents = new ArrayList<>();
    private int time = 0;

    public IEvent next() {
        if(allEvents.isEmpty())
            return null;
        IEvent currentEvent = allEvents.get(0);
        time = currentEvent.getTime();
        allEvents.remove(currentEvent);
        return currentEvent;
    }

    public void addEvent(IEvent event) {
        if (event.getTime() < time)
            throw new PastEventException();
        allEvents.add(event);
        allEvents.sort(Comparator.comparingInt(IEvent::getTime));
    }

    public void execute() {
        while (!allEvents.isEmpty()) {
            IEvent next = next();
            next.execute();
        }
    }
}
