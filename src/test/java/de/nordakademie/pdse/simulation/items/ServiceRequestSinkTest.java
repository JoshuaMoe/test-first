package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.servicerequest.ServiceRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ServiceRequestSinkTest {

    private ServiceRequest serviceRequest7;
    private ServiceRequest serviceRequest9;
    private ServiceRequestSink serviceRequestSink;

    @Before
    public void beforeEach() {
        serviceRequest7 = new ServiceRequest(7);
        serviceRequest9 = new ServiceRequest(9);
        serviceRequestSink = new ServiceRequestSink();
    }

    @Test
    public void testTwoRequests() {
        serviceRequestSink.acceptServiceRequest(serviceRequest7);
        serviceRequestSink.acceptServiceRequest(serviceRequest9);

        String protocol = serviceRequestSink.protocol();
        assertTrue(protocol.contains(serviceRequest7.getId().toString()));
        assertTrue(protocol.contains(serviceRequest9.getId().toString()));
        assertTrue(protocol.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"));
    }

}
