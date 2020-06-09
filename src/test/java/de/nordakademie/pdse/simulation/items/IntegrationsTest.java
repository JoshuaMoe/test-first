package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.items.ISimulationItem;
import de.nordakademie.pdse.simulation.items.ServiceRequestSink;
import de.nordakademie.pdse.simulation.items.ServiceRequestSource;
import de.nordakademie.pdse.simulation.timeline.ITimer;
import de.nordakademie.pdse.simulation.timeline.TimeLine;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationsTest {

    private ServiceRequestSource serviceRequestSource;
    private ITimer timer;
    private TimeLine timeLine;

    @Before
    public void beforeEach() {
        timer = mock(ITimer.class);
        timeLine = new TimeLine();
        serviceRequestSource = new ServiceRequestSource(timer, timeLine);
    }

    @Test()
    public void testIntegrationOfSourceAndSink() {
        when(timer.getTimeIntervall()).thenReturn(2,3,6,-1);
        ServiceRequestSink serviceRequestSink = new ServiceRequestSink();
        serviceRequestSource.addSuccessor(serviceRequestSink);

        serviceRequestSource.init();
        timeLine.execute();

        assertThat(serviceRequestSink.protocol(), containsString("creationTime>2"));
        assertThat(serviceRequestSink.protocol(), containsString("creationTime>3"));
        assertThat(serviceRequestSink.protocol(), containsString("creationTime>6"));
        assertThat(serviceRequestSink.protocol(), not(containsString("creationTime>-1")));
    }
}
