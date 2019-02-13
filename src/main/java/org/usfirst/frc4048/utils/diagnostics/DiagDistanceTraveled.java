package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Base class for Diagnosable that have an initial value (their "current" value) and that are required to change their
 * value by a certain amount from that
 */
public abstract class DiagDistanceTraveled implements Diagnosable {

    protected String name;
    protected int requiredTravel;
    protected NetworkTableEntry networkTableEntry;
    private int initialValue;
    private boolean traveledDistance;


    public DiagDistanceTraveled(String name, int requiredTravel, ShuffleboardTab shuffleboardTab) {
        this.name = name;
        this.requiredTravel = requiredTravel;

        networkTableEntry = shuffleboardTab.add(name, false).getEntry();
    }

    /**
     * Do not use for testing only
     */
    public DiagDistanceTraveled(String name, int requiredTravel, boolean DO_NOT_USE_FOR_TESTING_ONLY) {
        this.name = name;
        this.requiredTravel = requiredTravel;
    }

    @Override
    public void refresh() {
        networkTableEntry.setBoolean(getDiagResult());
    }

    @Override
    public void reset() {
        traveledDistance = false;
        initialValue = getCurrentValue();
    }

    boolean getDiagResult() {
        int currentValue = getCurrentValue();

        if (Math.abs(currentValue - initialValue) >= requiredTravel) {
            traveledDistance = true;
        }

        return this.traveledDistance;
    }

    protected abstract int getCurrentValue();
}
