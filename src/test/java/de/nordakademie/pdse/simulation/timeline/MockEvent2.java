package de.nordakademie.pdse.simulation.timeline;

public class MockEvent2 extends MockEvent{

    private final TimeLine timeLine;
    private final int nextTime;

    public MockEvent2(int time, TimeLine timeLine, int nextTime) {
        super(time);
        this.timeLine = timeLine;
        this.nextTime = nextTime;
    }

    @Override
    public void execute() {
        super.execute();
        timeLine.addEvent(new MockEvent(nextTime));
    }
}
