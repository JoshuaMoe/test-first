package de.nordakademie.pdse.simulation.timeline;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TimeLineTest {
    private TimeLine timeLine;

    @Before
    public void beforeTest() {
        timeLine = new TimeLine();
    }

    @Test
    public void testAddEventTime0() {
        IEvent event = new MockEvent(0);
        timeLine.addEvent(event);
        assertThat(timeLine.next(), is(event));
    }

    @Test(expected = PastEventException.class)
    public void testAddEventNegativeTime() {
        IEvent event = new MockEvent(-1);
        timeLine.addEvent(event);
    }


    @Test(expected = PastEventException.class)
    public void testAddEvent10AddEvent5() {
        IEvent event10 = new MockEvent(10);
        IEvent event5 = new MockEvent(5);

        timeLine.addEvent(event10);
        assertThat(timeLine.next(), is(event10));
        timeLine.addEvent(event5);
    }

    @Test()
    public void testAddEvent5AddEvent10() {
        IEvent event5 = new MockEvent(5);
        IEvent event10 = new MockEvent(10);

        timeLine.addEvent(event5);
        timeLine.next();
        timeLine.addEvent(event10);
        assertThat(timeLine.next(), is(event10));
    }

    @Test()
    public void testAddEvent10AddEvent10() {
        IEvent event10_0 = new MockEvent(10);
        IEvent event10_1 = new MockEvent(10);

        timeLine.addEvent(event10_0);
        timeLine.next();
        timeLine.addEvent(event10_1);
        assertThat(timeLine.next(), is(event10_1));
    }

    @Test()
    public void testAddEvent5AddEvent10NextNext() {
        IEvent event5 = new MockEvent(5);
        IEvent event10 = new MockEvent(10);

        timeLine.addEvent(event10);
        timeLine.addEvent(event5);
        assertThat(timeLine.next(), is(event5));
        assertThat(timeLine.next(), is(event10));
    }

    @Test()
    public void testAddEvent15AddEvent5AddEvent10() {
        IEvent event5 = new MockEvent(5);
        IEvent event10 = new MockEvent(10);
        IEvent event15 = new MockEvent(15);

        timeLine.addEvent(event15);
        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.next();
        assertThat(timeLine.next(), is(event10));
    }

    @Test()
    public void testAddEvent5AddEvent10NextNextNext() {
        IEvent event5 = new MockEvent(5);
        IEvent event10 = new MockEvent(10);

        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.next();
        timeLine.next();
        assertNull(timeLine.next());
    }

    @Test()
    public void testAddEvent15AddEvent5AddEvent10Excecute() {
        MockEvent event5 = new MockEvent(5);
        MockEvent event10 = new MockEvent(10);
        MockEvent event15 = new MockEvent(15);

        timeLine.addEvent(event15);
        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.execute();
        assertTrue(event5.timeExecuteCalled() < event10.timeExecuteCalled());
        assertTrue(event10.timeExecuteCalled() < event15.timeExecuteCalled());
    }

    @Test()
    public void testAddEvent15AddEvent5ThisAddsEvent10Excecute() {
        MockEvent event10 = new MockEvent(10);
        MockEvent event5 = new MockEvent(5, () -> timeLine.addEvent(event10));
        MockEvent event15 = new MockEvent(15);

        timeLine.addEvent(event15);
        timeLine.addEvent(event5);
        timeLine.execute();
        assertTrue(event5.timeExecuteCalled() < event10.timeExecuteCalled());
        assertTrue(event10.timeExecuteCalled() < event15.timeExecuteCalled());
    }
}
