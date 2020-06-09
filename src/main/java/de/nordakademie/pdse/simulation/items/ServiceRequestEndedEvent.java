package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.timeline.IEvent;

public class ServiceRequestEndedEvent implements IEvent {
    private final int time;
    private final ISimulationItem successor;
    private final ISimulationItem predecessor;

    public ServiceRequestEndedEvent(int time, ISimulationItem successor, ISimulationItem predecessor) {
        this.time = time;
        this.successor = successor;
        this.predecessor = predecessor;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public void execute() {
        successor.acceptServiceRequest(predecessor.pullRequest());
    }
}
