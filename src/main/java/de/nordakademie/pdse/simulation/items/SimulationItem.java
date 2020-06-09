package de.nordakademie.pdse.simulation.items;

import de.nordakademie.pdse.simulation.timeline.NoMoreConnectorsException;

public abstract class SimulationItem implements ISimulationItem {
    protected ISimulationItem successor;

    @Override
    public void addSuccessor(ISimulationItem successor) {
        if (this.successor != null)
            throw new NoMoreConnectorsException();
        this.successor = successor;
    }
}
