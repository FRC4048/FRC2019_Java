package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * A diagnostics class for analog potentiometer. The diagnostics will turn green once the pot has reached
 * both the MinVoltage and MaxVoltage points
 */
public class DiagPot implements Diagnosable {

    private String name;
    private double minVoltage;
    private double maxVoltage;
    private AnalogPotentiometer pot;
    private NetworkTableEntry networkTableEntry;

    private boolean seenMinVoltage;
    private boolean seenMaxVoltage;

    /**
     * Constructor
     *
     * @param name            - the name of the unit. Will be used on the Shuffleboard
     * @param minVoltage      - the minimum value the pot needs to hit to qualify for success
     * @param maxVoltage      - the maximum value the pot needs to hit to qualify for success
     * @param pot             - the pot instance to test
     * @param shuffleboardTab - the Shuffleboard tab to add the tile to
     */
    public DiagPot(String name, double minVoltage, double maxVoltage, AnalogPotentiometer pot, ShuffleboardTab shuffleboardTab) {
        this.name = name;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.pot = pot;

        networkTableEntry = shuffleboardTab.add(name, false).getEntry();

        reset();
    }

    @Override
    public void refresh() {
        double potValue = pot.get();
        if (potValue >= maxVoltage) {
            seenMaxVoltage = true;
        } else if (potValue <= minVoltage) {
            seenMinVoltage = true;
        }

        networkTableEntry.setBoolean(seenMaxVoltage && seenMinVoltage);
    }

    @Override
    public void reset() {
        seenMinVoltage = seenMaxVoltage = false;
    }
}
