package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * A diagnostics class for digital encoder. The diagnostics will turn green once the encoder has traveled at least a given
 * distance from its initial position (measured at initialization or after a reset)
 */
public class DiagEncoder implements Diagnosable {

    private String name;
    private int requiredTravel;
    private Encoder encoder;
    private NetworkTableEntry networkTableEntry;

    private int initialValue;
    private boolean traveledDistance;

    /**
     * Constructor
     *
     * @param name            - the name of the unit. Will be used on the Shuffleboard
     * @param requiredTravel  - the required difference between the initial position to qualify for success
     * @param encoder         - the encoder instance to test
     * @param shuffleboardTab - the Shuffleboard tab to add the tile to
     */
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
