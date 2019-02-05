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

    @Override
    public void refresh() {
        boolean currentValue = digitalInput.get();
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
