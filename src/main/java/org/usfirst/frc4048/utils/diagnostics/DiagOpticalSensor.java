package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DiagOpticalSensor implements Diagnosable {

    private String name;
    private DigitalInput digitalInput;
    private NetworkTableEntry networkTableEntry;

    private boolean seenTrue;
    private boolean seenFalse;

    /**
     * Constructor
     * 
     * @param name            - The sensor's name, which will be shown on Shuffleboard
     * @param digitalInput    - The DigitalInput pin the sensor is connected to
     * @param shuffleboardTab - the Shuffleboard tab to add the tile to
     */
    public DiagOpticalSensor(String name, DigitalInput digitalInput, ShuffleboardTab shuffleboardTab){
        this.name = name;
        this.digitalInput = digitalInput;

        this.networkTableEntry = shuffleboardTab.add(name, false).getEntry();

        reset();
    }

    /**
     * Constructor FOR TESTING ONLY! DO NOT USE THIS FOR ANYTHING ELSE!
     * 
     * @param name                          - The sensor's name, which will be shown on Shuffleboard
     * @param digitalInput                  - The DigitalInput pin the sensor is connected to
     * @param DO_NOT_USE_THIS_TESTING_ONLY  - THIS IS ONLY FOR TESTING PURPOSES!
     */
    public DiagOpticalSensor(String name, DigitalInput digitalInput, boolean DO_NOT_USE_THIS_TESTING_ONLY){
        this.name = name;
        this.digitalInput = digitalInput;

        reset();
    }

    @Override
    public void refresh() {
        
        networkTableEntry.setBoolean(getDiagResult());
    }

    @Override
    public void reset() {
        seenFalse = seenTrue = false;
    }

    //Package protected method
    boolean getDiagResult() {
        boolean currentValue = digitalInput.get();
        if (currentValue) {
            seenTrue = true;
        } else {
            seenFalse = true;
        }

        return seenTrue && seenFalse;
    }
}
