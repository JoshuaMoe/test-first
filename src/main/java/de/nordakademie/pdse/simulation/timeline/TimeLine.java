package de.nordakademie.pdse.simulation.timeline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimeLine {
    private final List<IEvent> allEvents = new ArrayList<>();
    private IEvent currentEvent;

    private IEvent next() {
        if(allEvents.isEmpty())
            return null;
        currentEvent = allEvents.get(0);
        allEvents.remove(currentEvent);
        return currentEvent;
    }

    public void addEvent(IEvent event) {
        if (event.getTime() < now())
            throw new PastEventException();
        allEvents.add(event);
        allEvents.sort(Comparator.comparingInt(IEvent::getTime));
    }

    public void execute() {
        while (next() != null) {
            currentEvent.execute();
        }
    }

    private int now () {
        return currentEvent != null ? currentEvent.getTime() : 0;
    }
}
