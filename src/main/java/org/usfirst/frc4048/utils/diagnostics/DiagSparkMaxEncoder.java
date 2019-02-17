package org.usfirst.frc4048.utils.diagnostics;

import com.revrobotics.CANSparkMax;

/**
 * A diagnostics class for digital encoder that is connected directly to the SparkMAX.
 * The diagnostics will turn green once the encoder has traveled at least a given
 * distance from its initial position (measured at initialization or after a reset)
 */
public class DiagSparkMaxEncoder extends DiagDistanceTraveled {

    private CANSparkMax canSparkMax;

    /**
     * Constructor
     *
     * @param name            - the name of the unit. Will be used on the Shuffleboard
     * @param requiredTravel  - the required difference between the initial position to qualify for success
     * @param canSparkMax     - the encoder instance to test
     */
    public DiagSparkMaxEncoder(String name, int requiredTravel, CANSparkMax canSparkMax) {
        super(name, requiredTravel);
        this.canSparkMax = canSparkMax;
        reset();
    }

    @Override
    protected int getCurrentValue() {
        return (int)canSparkMax.getEncoder().getPosition();
    }
}
