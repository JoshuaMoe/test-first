package de.nordakademie.pdse.simulation.timeline;

public class MockEvent implements IEvent{

    private int time;
    private long executeCalled;
    private Runnable action;

    public MockEvent(int time) {
        this.time = time;
    }

    public MockEvent(int time, Runnable action) {
        this(time);
        this.action = action;
    }

    public int getTime() {
        return time;
    }

    public void execute() {
        if(action != null)
            action.run();
        executeCalled = System.nanoTime();
    }

    public long timeExecuteCalled() {
        return executeCalled;
    }
}
