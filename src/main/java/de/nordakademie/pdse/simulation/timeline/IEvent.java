package de.nordakademie.pdse.simulation.timeline;

public interface IEvent {
    int getTime();
    void execute();
}
