package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.servicerequest.ServiceRequest;
import de.nordakademie.pdse.simulation.timeline.*;

public class ServiceRequestSource implements ISimulationItem {
    private ISimulationItem successor;
    private final ITimer timer;
    private final TimeLine timeLine;

    public ServiceRequestSource(ITimer timer, TimeLine timeLine) {
        this.timer = timer;
        this.timeLine = timeLine;
    }

    public void init() {
        int timeIntervall = timer.getTimeIntervall();
        if(timeIntervall != -1) {
            timeLine.addEvent(new IEvent() {
                @Override
                public int getTime() {
                    return timeIntervall;
                }

                @Override
                public void execute() {
                    successor.acceptServiceRequest(new ServiceRequest(getTime()));
                }
            });
            init();
        }
    }

    public void execute () {
        timeLine.execute();
    }

    @Override
    public void acceptServiceRequest(ServiceRequest serviceRequest) {
        throw new RuntimeException("STUB");
    }

    @Override
    public void addSuccessor(ISimulationItem successor) {
        if (this.successor != null)
            throw new NoMoreConnectorsException();
        this.successor = successor;
    }

    @Override
    public void addPredecessor(ISimulationItem predecessor) {
        throw new RuntimeException("STUB");
    }

    @Override
    public ServiceRequest pullRequest() {
        throw new RuntimeException("STUB");
    }
}
