package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Diagnostics class for digital switch; it is a DiagBoolean object.
 */
public class DiagSwitch extends DiagBoolean{

    /**
     * Constructor
     *
     * @param name            the name of the unit. Will be used on the Shuffleboard
     * @param digitalInput    - the DigitalInput the switch is connected to
     * @param shuffleboardTab - the Shuffleboard tab to add the tile to
     */
    public DiagSwitch(String name, DigitalInput digitalInput, ShuffleboardTab shuffleboardTab) {
        super(name, digitalInput, shuffleboardTab);
    }

    /**
     * Constructor FOR TESTING ONLY DO NOT USE
     */
    public DiagSwitch(String name, DigitalInput digitalInput, boolean FOR_TESTING_ONLY_DO_NOT_USE) {
        super(name, digitalInput, FOR_TESTING_ONLY_DO_NOT_USE);
    }

}
