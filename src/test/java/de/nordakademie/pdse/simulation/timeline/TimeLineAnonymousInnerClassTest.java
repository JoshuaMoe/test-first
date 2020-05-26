package de.nordakademie.pdse.simulation.timeline;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TimeLineAnonymousInnerClassTest {
    private TimeLine timeLine;

    @Before
    public void beforeTest() {
        timeLine = new TimeLine();
    }

    @Test
    public void testAddEventTime0() {
        IEvent event = new IEvent() {
            @Override
            public int getTime() {
                return 0;
            }

            @Override
            public void execute() {

            }
        };
        timeLine.addEvent(event);
        assertThat(timeLine.next(), is(event));
    }

    @Test(expected = PastEventException.class)
    public void testAddEventNegativeTime() {
        IEvent event = new IEvent() {
            @Override
            public int getTime() {
                return -1;
            }

            @Override
            public void execute() {

            }
        };
        timeLine.addEvent(event);
    }


    @Test(expected = PastEventException.class)
    public void testAddEvent10AddEvent5() {
        IEvent event10 = new IEvent() {
            @Override
            public int getTime() {
                return 10;
            }

            @Override
            public void execute() {

            }
        };
        IEvent event5 = new IEvent() {
            @Override
            public int getTime() {
                return 5;
            }

            @Override
            public void execute() {

            }
        };

        timeLine.addEvent(event10);
        assertThat(timeLine.next(), is(event10));
        timeLine.addEvent(event5);
    }

    @Test()
    public void testAddEvent5AddEvent10() {
        IEvent event10 = new IEvent() {
            @Override
            public int getTime() {
                return 10;
            }

            @Override
            public void execute() {

            }
        };
        IEvent event5 = new IEvent() {
            @Override
            public int getTime() {
                return 5;
            }

            @Override
            public void execute() {

            }
        };

        timeLine.addEvent(event5);
        timeLine.next();
        timeLine.addEvent(event10);
        assertThat(timeLine.next(), is(event10));
    }

    @Test()
    public void testAddEvent10AddEvent10() {
        IEvent event10_0 = new IEvent() {
            @Override
            public int getTime() {
                return 10;
            }

            @Override
            public void execute() {

            }
        };
        IEvent event10_1 = new IEvent() {
            @Override
            public int getTime() {
                return 10;
            }

            @Override
            public void execute() {

            }
        };

        timeLine.addEvent(event10_0);
        timeLine.next();
        timeLine.addEvent(event10_1);
        assertThat(timeLine.next(), is(event10_1));
    }

    @Test()
    public void testAddEvent5AddEvent10NextNext() {
        IEvent event10 = new IEvent() {
            @Override
            public int getTime() {
                return 10;
            }

            @Override
            public void execute() {

            }
        };
        IEvent event5 = new IEvent() {
            @Override
            public int getTime() {
                return 5;
            }

            @Override
            public void execute() {

            }
        };

        timeLine.addEvent(event10);
        timeLine.addEvent(event5);
        assertThat(timeLine.next(), is(event5));
        assertThat(timeLine.next(), is(event10));
    }

    @Test()
    public void testAddEvent15AddEvent5AddEvent10() {
        IEvent event15 = new IEvent() {
            @Override
            public int getTime() {
                return 15;
            }

            @Override
            public void execute() {

            }
        };
        IEvent event10 = new IEvent() {
            @Override
            public int getTime() {
                return 10;
            }

            @Override
            public void execute() {

            }
        };
        IEvent event5 = new IEvent() {
            @Override
            public int getTime() {
                return 5;
            }

            @Override
            public void execute() {

            }
        };

        timeLine.addEvent(event15);
        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.next();
        assertThat(timeLine.next(), is(event10));
    }

    @Test()
    public void testAddEvent5AddEvent10NextNextNext() {
        IEvent event10 = new IEvent() {
            @Override
            public int getTime() {
                return 10;
            }

            @Override
            public void execute() {

            }
        };
        IEvent event5 = new IEvent() {
            @Override
            public int getTime() {
                return 5;
            }

            @Override
            public void execute() {

            }
        };

        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.next();
        timeLine.next();
        assertNull(timeLine.next());
    }

    @Test()
    public void testAddEvent15AddEvent5AddEvent10Excecute() {
        List<IEvent> orderExcecuted = new ArrayList<>();
        IEvent event15 = new IEvent() {
            @Override
            public int getTime() {
                return 15;
            }

            @Override
            public void execute() {
                orderExcecuted.add(this);
            }
        };
        IEvent event10 = new IEvent() {
            @Override
            public int getTime() {
                return 10;
            }

            @Override
            public void execute() {
                orderExcecuted.add(this);
            }
        };
        IEvent event5 = new IEvent() {
            @Override
            public int getTime() {
                return 5;
            }

            @Override
            public void execute() {
                orderExcecuted.add(this);
            }
        };

        timeLine.addEvent(event15);
        timeLine.addEvent(event5);
        timeLine.addEvent(event10);
        timeLine.execute();
        assertThat(orderExcecuted.get(0), is(event5));
        assertThat(orderExcecuted.get(1), is(event10));
        assertThat(orderExcecuted.get(2), is(event15));
    }

    @Test()
    public void testAddEvent15AddEvent5ThisAddsEvent10Excecute() {
        List<IEvent> orderExcecuted = new ArrayList<>();
        IEvent event15 = new IEvent() {
            @Override
            public int getTime() {
                return 15;
            }

            @Override
            public void execute() {
                orderExcecuted.add(this);
            }
        };
        IEvent event5 = new IEvent() {
            @Override
            public int getTime() {
                return 5;
            }

            @Override
            public void execute() {
                orderExcecuted.add(this);
                timeLine.addEvent(new IEvent() {
                    @Override
                    public int getTime() {
                        return 10;
                    }

                    @Override
                    public void execute() {
                    }
                });
            }
        };

        timeLine.addEvent(event15);
        timeLine.addEvent(event5);
        timeLine.execute();
        assertThat(orderExcecuted.get(0), is(event5));
        assertThat(orderExcecuted.get(2), is(event15));
    }
}
