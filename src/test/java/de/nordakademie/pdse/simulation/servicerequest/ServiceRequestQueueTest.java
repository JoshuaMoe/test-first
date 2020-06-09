package de.nordakademie.pdse.simulation.servicerequest;


import de.nordakademie.pdse.simulation.items.ISimulationItem;
import de.nordakademie.pdse.simulation.items.ServiceLevel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceRequestQueueTest {
    private ServiceLevel succesor;
    private ServiceRequestQueue serviceRequestQueue;
    private ServiceRequest serviceRequest1;
    private ServiceRequest serviceRequest2;

    @Before
    public void beforeEach() {
        succesor = mock(ServiceLevel.class);
        serviceRequest1 = mock(ServiceRequest.class);
        serviceRequest2 = mock(ServiceRequest.class);
        serviceRequestQueue = new ServiceRequestQueue();
    }

    @Test
    public void testBusyFalse() {
        assertFalse(serviceRequestQueue.busy());
    }

    @Test
    public void testAddSucc() {
        serviceRequestQueue.addSuccessor(succesor);
        serviceRequestQueue.acceptServiceRequest(null);

        verify(succesor).acceptServiceRequest(any());
    }


    @Test
    public void testAddSuccBusy() {
        when(succesor.busy()).thenReturn(true);
        serviceRequestQueue.addSuccessor(succesor);
        serviceRequestQueue.acceptServiceRequest(null);

        verify(succesor, never()).acceptServiceRequest(any());
    }

    @Test
    public void testPR() {
        assertNull(serviceRequestQueue.pullRequest());
    }


    @Test
    public void testPRQ() {
        when(succesor.busy()).thenReturn(true);
        serviceRequestQueue.addSuccessor(succesor);
        serviceRequestQueue.acceptServiceRequest(serviceRequest1);
        serviceRequestQueue.acceptServiceRequest(serviceRequest2);

        assertEquals(serviceRequestQueue.pullRequest(), serviceRequest1);
        assertEquals(serviceRequestQueue.pullRequest(), serviceRequest2);
        assertNull(serviceRequestQueue.pullRequest());
    }
}