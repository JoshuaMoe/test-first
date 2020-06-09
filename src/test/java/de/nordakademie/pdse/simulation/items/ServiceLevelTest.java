package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.servicerequest.ServiceRequest;
import de.nordakademie.pdse.simulation.timeline.IEvent;
import de.nordakademie.pdse.simulation.timeline.ITimer;
import de.nordakademie.pdse.simulation.timeline.NoMoreConnectorsException;
import de.nordakademie.pdse.simulation.timeline.TimeLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ServiceLevelTest {

    private ServiceLevel serviceLevel;
    private ISimulationItem succesor;
    private ISimulationItem predecesor;
    private ServiceRequest serviceRequest;
    private ITimer iTimer;
    private TimeLine timeLine;

    @Before
    public void beforeEach() {
        succesor = mock(ISimulationItem.class);
        predecesor = mock(ISimulationItem.class);
        serviceRequest = mock(ServiceRequest.class);
        iTimer = mock(ITimer.class);
        timeLine = mock(TimeLine.class);
        when(timeLine.now()).thenReturn(3);
        when(iTimer.getTimeIntervall()).thenReturn(7);
        serviceLevel = new ServiceLevel(2, iTimer, timeLine);
    }

    @Test
    public void testAddSucc() {
        serviceLevel.addSuccessor(succesor);
    }

    @Test(expected = NoMoreConnectorsException.class)
    public void testAddTwoSucc() {
        serviceLevel.addSuccessor(succesor);
        serviceLevel.addSuccessor(succesor);
    }

    @Test(expected = BusyException.class)
    public void testNoWorkersAvailable() {
        serviceLevel = new ServiceLevel(0);
        serviceLevel.acceptServiceRequest(serviceRequest);
    }

    @Test
    public void testNowWorkersAvailable() {
        serviceLevel.acceptServiceRequest(serviceRequest);
    }

    @Test(expected = BusyException.class)
    public void testActualWork() {
        serviceLevel = new ServiceLevel(1, iTimer, timeLine);
        serviceLevel.acceptServiceRequest(serviceRequest);
        serviceLevel.acceptServiceRequest(serviceRequest);
    }

    @Test(expected = BusyException.class)
    public void testDoesntHappenInReality() {
        serviceLevel = new ServiceLevel(-1);
        serviceLevel.acceptServiceRequest(serviceRequest);
    }

    @Test
    public void testTimer() {
        serviceLevel.acceptServiceRequest(serviceRequest);
        verify(iTimer).getTimeIntervall();
    }

    @Test
    public void testRealTimerNUM8() {
        serviceLevel.acceptServiceRequest(serviceRequest);

        verify(timeLine).addEvent(argThat(event -> event.getTime() == 10));
    }

    @Test
    public void testBusyFalse() {
        assertFalse(serviceLevel.busy());
    }

    @Test
    public void testNUmber10() {
        serviceLevel.acceptServiceRequest(serviceRequest);
        serviceLevel.acceptServiceRequest(serviceRequest);
        assertTrue(serviceLevel.busy());
    }

    @Test(expected = BusyException.class)
    public void testNUmber11() {
        serviceLevel.acceptServiceRequest(serviceRequest);
        serviceLevel.acceptServiceRequest(serviceRequest);
        serviceLevel.acceptServiceRequest(serviceRequest);
    }

    @Test
    public void testNUmber12() {
        serviceLevel.addSuccessor(succesor);
        serviceLevel.addPredecessor(predecesor);
        serviceLevel.acceptServiceRequest(serviceRequest);

        ArgumentCaptor<IEvent> c = ArgumentCaptor.forClass(IEvent.class);
        verify(timeLine).addEvent(c.capture());

        IEvent event = c.getValue();
        event.execute();

        verify(succesor).acceptServiceRequest(any());
    }

    @Test
    public void testNUmber13() {
        serviceLevel.addPredecessor(predecesor);
    }

    @Test(expected = NoMoreConnectorsException.class)
    public void testNUmber14() {
        serviceLevel.addPredecessor(predecesor);
        serviceLevel.addPredecessor(predecesor);
    }

    @Test
    public void testNUmber15() {
        serviceLevel.addPredecessor(predecesor);
        serviceLevel.addSuccessor(succesor);
        serviceLevel.acceptServiceRequest(serviceRequest);
        serviceLevel.acceptServiceRequest(serviceRequest);

        ArgumentCaptor<IEvent> c = ArgumentCaptor.forClass(IEvent.class);
        verify(timeLine, times(2)).addEvent(c.capture());

        IEvent event = c.getAllValues().get(0);
        event.execute();

        verify(predecesor).pullRequest();

        assertTrue(serviceLevel.busy());
    }

    @Test
    public void testNUmber16() {
        ServiceRequest serviceRequest = mock(ServiceRequest.class);
        when(predecesor.pullRequest()).thenReturn(serviceRequest);
        serviceLevel.addPredecessor(predecesor);
        serviceLevel.addSuccessor(succesor);
        serviceLevel.acceptServiceRequest(serviceRequest);
        serviceLevel.acceptServiceRequest(serviceRequest);

        ArgumentCaptor<IEvent> c = ArgumentCaptor.forClass(IEvent.class);
        verify(timeLine, times(2)).addEvent(c.capture());

        IEvent event = c.getAllValues().get(0);
        event.execute();

        verify(predecesor).pullRequest();

        assertTrue(serviceLevel.busy());
    }
}