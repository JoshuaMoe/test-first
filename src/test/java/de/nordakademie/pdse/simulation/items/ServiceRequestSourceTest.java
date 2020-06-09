package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.servicerequest.ServiceRequest;
import de.nordakademie.pdse.simulation.timeline.*;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServiceRequestSourceTest {

    private ServiceRequestSource serviceRequestSource;
    private ISimulationItem iSimulationItem;
    private ITimer timer;
    private TimeLine timeLine;

    @Before
    public void beforeEach() {
        timer = mock(ITimer.class);
        timeLine = mock(TimeLine.class);
        serviceRequestSource = new ServiceRequestSource(timer, timeLine);
        iSimulationItem = mock(ISimulationItem.class);
    }

    @Test(expected = NoMoreSuccessorException.class)
    public void testAddTwoSuccessor() {
        serviceRequestSource.addSuccessor(iSimulationItem);
        serviceRequestSource.addSuccessor(iSimulationItem);
    }

    @Test()
    public void testSecondTest() {
        when(timer.getTimeIntervall()).thenReturn(3,5,7,-1);

        serviceRequestSource.init();
        verify(timeLine, times(3)).addEvent(isA(IEvent.class));
    }

    @Test()
    public void testThirdTest() {
        serviceRequestSource = new ServiceRequestSource(timer, timeLine);

        when(timer.getTimeIntervall()).thenReturn(7, -1);

        serviceRequestSource.init();
        verify(timeLine).addEvent(argThat(event -> event.getTime() == 7));
    }

    @Test()
    public void testFourthTest() {
        timeLine = new TimeLine();
        serviceRequestSource = new ServiceRequestSource(timer, timeLine);
        serviceRequestSource.addSuccessor(iSimulationItem);

        when(timer.getTimeIntervall()).thenReturn(7, -1);

        serviceRequestSource.init();
        serviceRequestSource.execute();

        ArgumentCaptor<ServiceRequest> argument = ArgumentCaptor.forClass(ServiceRequest.class);
        verify(iSimulationItem).acceptServiceRequest(argument.capture());
        assertThat(argument.getValue().getCreationTime(), is(7));
    }
}