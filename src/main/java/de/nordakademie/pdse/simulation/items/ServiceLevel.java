package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.servicerequest.ServiceRequest;
import de.nordakademie.pdse.simulation.timeline.ITimer;
import de.nordakademie.pdse.simulation.timeline.NoMoreConnectorsException;
import de.nordakademie.pdse.simulation.timeline.TimeLine;

public class ServiceLevel implements ISimulationItem {
    private ISimulationItem successor;
    private ISimulationItem predecessor;
    private int numberOfFreeWorkers;
    private TimeLine timeLine;
    private ITimer timer;

    public ServiceLevel(int numberOfFreeWorkers) {
        this.numberOfFreeWorkers = numberOfFreeWorkers;
    }

    public ServiceLevel(int numberOfFreeWorkers, ITimer timer, TimeLine timeLine) {
        this(numberOfFreeWorkers);
        this.timer = timer;
        this.timeLine = timeLine;
    }

    @Override
    public void acceptServiceRequest(ServiceRequest serviceRequest) {
        if (busy())
            throw new BusyException();
        numberOfFreeWorkers--;
        if (timer != null) {
            timeLine.addEvent(new ServiceRequestEndedEvent(timer.getTimeIntervall() + timeLine.now(), successor, predecessor));
        }
    }

    @Override
    public void addSuccessor(ISimulationItem successor) {
        if (this.successor != null)
            throw new NoMoreConnectorsException();
        this.successor = successor;
    }

    public boolean busy() {
        return numberOfFreeWorkers <= 0;
    }

    @Override
    public void addPredecessor(ISimulationItem predecessor) {
        if (this.predecessor != null)
            throw new NoMoreConnectorsException();
        this.predecessor = predecessor;
    }

    @Override
    public ServiceRequest pullRequest() {
        return null;
    }
}
