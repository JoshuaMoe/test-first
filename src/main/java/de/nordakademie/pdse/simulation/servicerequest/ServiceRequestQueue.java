package de.nordakademie.pdse.simulation.servicerequest;

import de.nordakademie.pdse.simulation.items.ISimulationItem;
import de.nordakademie.pdse.simulation.items.ServiceLevel;
import de.nordakademie.pdse.simulation.items.SimulationItem;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ServiceRequestQueue extends SimulationItem {
    private Queue<ServiceRequest> queue = new LinkedList<>();
    public boolean busy() {
        return false;
    }

    @Override
    public void acceptServiceRequest(ServiceRequest serviceRequest) {
        ServiceLevel successor = (ServiceLevel) this.successor;
        if (!successor.busy())
            successor.acceptServiceRequest(serviceRequest);
        else {
            queue.add(serviceRequest);
        }
    }

    @Override
    public void addPredecessor(ISimulationItem predecessor) {

    }

    @Override
    public ServiceRequest pullRequest() {
        return queue.isEmpty() ? null : queue.remove();
    }
}
