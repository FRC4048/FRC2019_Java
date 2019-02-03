package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DiagEncoder implements Diagnosable {

    private String name;
    private int requiredTravel;
    private Encoder encoder;
    private NetworkTableEntry networkTableEntry;

    private int initialValue;
    private boolean traveledDistance;

    public DiagEncoder(String name, int requiredTravel, Encoder encoder, ShuffleboardTab shuffleboardTab) {
        this.name = name;
        this.requiredTravel = requiredTravel;
        this.encoder = encoder;

        networkTableEntry = shuffleboardTab.add(name, false).getEntry();

        reset();
    }

    @Override
    public void refresh() {
        int encoderValue = encoder.get();

        if (Math.abs(encoderValue - initialValue) >= requiredTravel) {
            traveledDistance = true;
        }

        networkTableEntry.setBoolean(traveledDistance);
    }

    @Override
    public void reset() {
        traveledDistance = false;
        initialValue = encoder.get();
    }
}
