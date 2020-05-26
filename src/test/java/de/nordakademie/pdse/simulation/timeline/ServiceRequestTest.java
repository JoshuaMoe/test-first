package de.nordakademie.pdse.simulation.timeline;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class ServiceRequestTest {

    @Test
    public void test() {
        ServiceRequest serviceRequest = new ServiceRequest();
        ServiceRequest serviceRequest1 = new ServiceRequest();

        assertNotEquals(serviceRequest.getId(), serviceRequest1.getId());
    }
}
