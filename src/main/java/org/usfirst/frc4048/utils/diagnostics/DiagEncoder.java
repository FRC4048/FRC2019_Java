package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * A diagnostics class for digital encoder. The diagnostics will turn green once the encoder has traveled at least a given
 * distance from its initial position (measured at initialization or after a reset)
 */
public class DiagEncoder extends DiagDistanceTraveled {

    private Encoder encoder;

    /**
     * Constructor
     *
     * @param name            - the name of the unit. Will be used on the Shuffleboard
     * @param requiredTravel  - the required difference between the initial position to qualify for success
     * @param encoder         - the encoder instance to test
     * @param shuffleboardTab - the Shuffleboard tab to add the tile to
     */
    public DiagEncoder(String name, int requiredTravel, Encoder encoder, ShuffleboardTab shuffleboardTab) {
        super(name, requiredTravel, shuffleboardTab);
        this.encoder = encoder;
        reset();
    }

    /**
     * Do not use for testing only
     */
    public DiagEncoder(String name, int requiredTravel, Encoder encoder, boolean DO_NOT_USE_FOR_TESTING_ONLY) {
        super(name, requiredTravel, true);
        this.encoder = encoder;
        reset();
    }

    @Override
    protected int getCurrentValue() {
        return encoder.get();
    }
}
