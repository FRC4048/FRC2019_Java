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
     */
    public DiagOpticalSensor(String name, DigitalInput digitalInput){
        super(name, digitalInput);
    }

}
