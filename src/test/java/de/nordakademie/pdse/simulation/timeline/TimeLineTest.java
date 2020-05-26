package de.nordakademie.pdse.simulation.timeline;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TimeLineTest {
    private TimeLine timeLine;
    private IEvent event5;
    private IEvent event10;
    private IEvent event15;

    @Before
    public void beforeTest() {
        timeLine = new TimeLine();
        event5 = mock(IEvent.class);
        event10 = mock(IEvent.class);
        event15 = mock(IEvent.class);

        when(event5.getTime()).thenReturn(5);
        when(event10.getTime()).thenReturn(10);
        when(event15.getTime()).thenReturn(15);
    }

    @Test
    public void testAddEventTime0() {
        timeLine.addEvent(event10);
        timeLine.execute();
        verify(event10).execute();
    }

    @Test(expected = PastEventException.class)
    public void testAddEventNegativeTime() {
        when(event10.getTime()).thenReturn(-1);
        timeLine.addEvent(event10);
        verify(event10).execute();
    }


    @Test(expected = PastEventException.class)
    public void testAddEvent10AddEvent5() {
        timeLine.addEvent(event10);
        timeLine.execute();
        timeLine.addEvent(event5);

        verify(event5).execute();
        verify(event10).execute();
    }

    @Test()
    public void testAddEvent5AddEvent10() {
        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.execute();

        verify(event5).execute();
        verify(event10).execute();
    }

    @Test()
    public void testAddEvent10AddEvent10() {
        when(event5.getTime()).thenReturn(10);

        timeLine.addEvent(event5);
        timeLine.addEvent(event10);

        timeLine.execute();

        verify(event5).execute();
        verify(event10).execute();
    }

    @Test()
    public void testAddEvent5AddEvent10NextNext() {
        timeLine.addEvent(event10);
        timeLine.addEvent(event5);
        timeLine.execute();

        verify(event5).execute();
        verify(event10).execute();
    }

    @Test()
    public void testAddEvent15AddEvent5AddEvent10() {
        timeLine.addEvent(event15);
        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.execute();

        verify(event5).execute();
        verify(event10).execute();
        verify(event15).execute();
    }

    @Test()
    public void testAddEvent5AddEvent10NextNextNext() {
        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.execute();

        verify(event5).execute();
        verify(event10).execute();
    }

    @Test()
    public void testAddEvent15AddEvent5AddEvent10Excecute() {
        timeLine.addEvent(event15);
        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.execute();

        InOrder inOrder = inOrder(event5, event10, event15);
        inOrder.verify(event5).execute();
        inOrder.verify(event10).execute();
        inOrder.verify(event15).execute();
    }

    @Test()
    public void testAddEvent15AddEvent5ThisAddsEvent10Excecute() {
        doAnswer(i -> {
            timeLine.addEvent(event10);
            return null;
        }).when(event5).execute();
        timeLine.addEvent(event15);
        timeLine.addEvent(event5);
        timeLine.execute();

        InOrder inOrder = inOrder(event5, event10, event15);
        inOrder.verify(event5).execute();
        inOrder.verify(event10).execute();
        inOrder.verify(event15).execute();
    }
}
