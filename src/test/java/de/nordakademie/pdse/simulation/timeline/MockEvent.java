package de.nordakademie.pdse.simulation.timeline;

public class MockEvent implements IEvent{

    private int time;
    private int executionOrder = 0;
    private static int executed = 0;

    public MockEvent(int time) {
        this.time = time;
    }


    public int getTime() {
        return time;
    }

    public void execute() {
        executionOrder = executed++;
    }

    public int getExecutionOrder() {
        return executionOrder;
    }
}
