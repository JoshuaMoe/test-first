package de.nordakademie.pdse.simulation.timeline;

import java.util.UUID;

public class ServiceRequest {

    private final UUID id;

    public ServiceRequest() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
