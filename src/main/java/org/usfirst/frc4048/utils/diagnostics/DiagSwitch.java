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
     */
    public DiagSwitch(String name, DigitalInput digitalInput) {
        super(name, digitalInput);
    }

}
