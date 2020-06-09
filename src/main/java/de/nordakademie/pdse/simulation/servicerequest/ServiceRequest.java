package de.nordakademie.pdse.simulation.servicerequest;

import java.util.UUID;

public class ServiceRequest {
    private final UUID id;
    private final int creationTime;

    public ServiceRequest(int creationTime) {
        this.creationTime = creationTime;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public String getProtocol () {
        StringBuilder builder = new StringBuilder("<id>");
        builder.append(id.toString());
        builder.append("</id>\n");
        builder.append("<creationTime>");
        builder.append(creationTime);
        builder.append("</creationTime>\n");
        return builder.toString();
    }
}
