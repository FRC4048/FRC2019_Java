package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DiagSwitch implements Diagnosable {

    private String name;
    private DigitalInput digitalInput;
    private NetworkTableEntry networkTableEntry;

    private boolean seenFalse;
    private boolean seenTrue;

    public DiagSwitch(String name, DigitalInput digitalInput, ShuffleboardTab shuffleboardTab) {
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
