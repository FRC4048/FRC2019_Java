package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Diagnostic class for the Optical sensor; it is a DiagBoolean object.
 */
public class DiagOpticalSensor extends DiagBoolean {

    /**
     * Constructor
     * 
     * @param name            - The sensor's name, which will be shown on Shuffleboard
     * @param digitalInput    - The DigitalInput pin the sensor is connected to
     * @param shuffleboardTab - the Shuffleboard tab to add the tile to
     */
    public DiagOpticalSensor(String name, DigitalInput digitalInput, ShuffleboardTab shuffleboardTab){
        super(name, digitalInput, shuffleboardTab);
    }

    /**
     * Constructor FOR TESTING ONLY! DO NOT USE THIS FOR ANYTHING ELSE!
     */
    public DiagOpticalSensor(String name, DigitalInput digitalInput, boolean DO_NOT_USE_THIS_TESTING_ONLY){
        super(name, digitalInput, DO_NOT_USE_THIS_TESTING_ONLY);
    }

}
