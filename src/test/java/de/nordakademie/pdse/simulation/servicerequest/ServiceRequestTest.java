package de.nordakademie.pdse.simulation.servicerequest;

import de.nordakademie.pdse.simulation.servicerequest.ServiceRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ServiceRequestTest {

    private ServiceRequest serviceRequest7;
    private ServiceRequest serviceRequest9;

    @Before
    public void beforeEach() {
        serviceRequest7 = new ServiceRequest(7);
        serviceRequest9 = new ServiceRequest(9);
    }

    @Test
    public void testDifferentIds() {
        assertNotEquals(serviceRequest7.getId(), serviceRequest9.getId());
    }

    @Test
    public void testProtocolTime7() {
        String protocol = serviceRequest7.getProtocol();
        assertTrue(protocol.contains(serviceRequest7.getId().toString()));
        assertTrue(protocol.contains("<creationTime>" + 7 + "</creationTime>"));
    }

    @Test
    public void testProtocolTime9() {
        String protocol = serviceRequest9.getProtocol();
        assertTrue(protocol.contains(serviceRequest9.getId().toString()));
        assertTrue(protocol.contains("<creationTime>" + 9 + "</creationTime>"));
    }
}
