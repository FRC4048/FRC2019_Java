package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Abstract diagnostics class for boolean sensors using DigitalInput
 * The diagnostics will turn green once the switch has changes to reflect both positions: ON and OFF
 */
public abstract class DiagBoolean implements Diagnosable {

    private String name;
    private DigitalInput digitalInput;
    private NetworkTableEntry networkTableEntry;

    private boolean seenFalse;
    private boolean seenTrue;

    /**
     * Constructor
     *
     * @param name            the name of the unit. Will be used on the Shuffleboard
     * @param digitalInput    - the DigitalInput the switch is connected to
     * @param shuffleboardTab - the Shuffleboard tab to add the tile to
     */
    public DiagBoolean(String name, DigitalInput digitalInput, ShuffleboardTab shuffleboardTab) {
        this.digitalInput = digitalInput;
        this.name = name;

        networkTableEntry = shuffleboardTab.add(name, false).getEntry();

        reset();
    }

    /**
     * Constructor FOR TESTING ONLY DO NOT USE
     *
     * @param name            the name of the unit. Will be used on the Shuffleboard
     * @param digitalInput    - the DigitalInput the switch is connected to
     * @param shuffleboardTab - the Shuffleboard tab to add the tile to
     */
    public DiagBoolean(String name, DigitalInput digitalInput, boolean FOR_TESTING_ONLY_DO_NOT_USE) {
        this.digitalInput = digitalInput;
        this.name = name;

        reset();
    }

    @Override
    public void refresh() {
        networkTableEntry.setBoolean(getDiagResult(digitalInput));
    }

    @Override
    public void reset() {
        seenFalse = seenTrue = false;
    }

    // Package protected
    boolean getDiagResult(DigitalInput myDigitalInput) {
        boolean currentValue = myDigitalInput.get();
        // Set the value for the state - whether the switch is pressed or not
        if (currentValue) {
            seenTrue = true;
        } else {
            seenFalse = true;
        }

        return seenTrue && seenFalse;
    }

}
