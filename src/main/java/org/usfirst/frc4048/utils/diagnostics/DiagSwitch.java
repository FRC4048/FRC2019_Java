package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;

/**
 * Diagnostics class for digital switch.
 * The diagnostics will turn green once the switch has changes to reflect both positions: ON and OFF
 */
public class DiagSwitch implements Diagnosable {

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
    public DiagSwitch(String name, DigitalInput digitalInput, ShuffleboardContainer shuffleboardTab) {
        this.digitalInput = digitalInput;
        this.name = name;

        networkTableEntry = shuffleboardTab.add(name, false).getEntry();

        reset();
    }

    @Override
    public void refresh() {
        boolean currentValue = digitalInput.get();
        // Set the value for the state - whether the switch is pressed or not
        if (currentValue) {
            seenTrue = true;
        } else {
            seenFalse = true;
        }

        networkTableEntry.setBoolean(seenTrue && seenFalse);
    }

    @Override
    public void reset() {
        seenFalse = seenTrue = false;
    }
}
