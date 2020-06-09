package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.servicerequest.ServiceRequest;

public interface ISimulationItem {
    void acceptServiceRequest(ServiceRequest serviceRequest);
    void addSuccessor(ISimulationItem successor);
    void addPredecessor(ISimulationItem predecessor);
    ServiceRequest pullRequest();
}
