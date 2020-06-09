package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.servicerequest.ServiceRequest;

public class ServiceRequestSink implements ISimulationItem{
    private final StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

    @Override
    public void acceptServiceRequest(ServiceRequest serviceRequest) {
        builder.append("<ServiceRequest>");
        builder.append(serviceRequest.getProtocol());
        builder.append("</ServiceRequest>");
    }

    public String protocol() {
        return builder.toString();
    }

    @Override
    public void addSuccessor(ISimulationItem successor) {

    }

    @Override
    public void addPredecessor(ISimulationItem predecessor) {

    }

    @Override
    public ServiceRequest pullRequest() {
        return null;
    }
}
