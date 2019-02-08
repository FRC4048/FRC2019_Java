package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Diagnostics class for the Sonar. It is a DiagMinMax object.
 * Give it a max and minimum distance to test.
 */
public class DiagSonar extends DiagMinMax {

    private Ultrasonic sonar;

    /**
     * Constructor
     * 
     * @param name                    -Name of the sensor, to be used on Shuffleboard
     * @param sonar                   -The Ultrasonic object to be tested.
     * @param shuffleboardTab         -The Shuffleboard tab to add the tile to.
     * @param minDistance             -The minimum testing distance for the sonar; the one that will be tested against.
     * @param maxDistance             -The maximum testing distance for the sonar; the one that will be tested against.
     */
    public DiagSonar(String name, Ultrasonic sonar, ShuffleboardTab shuffleboardTab, double minDistance, double maxDistance){
        super(name, minDistance, maxDistance, shuffleboardTab);
        this.sonar = sonar;
    }

    /**
     * Constructor
     * DO NOT USE THIS; THIS IS FOR TESTING PURPOSES ONLY
     */
    public DiagSonar(String name, Ultrasonic sonar, boolean DO_NOT_USE_TESTING_ONLY, double minDistance, double maxDistance){
        super(name, minDistance, maxDistance, DO_NOT_USE_TESTING_ONLY);
        this.sonar = sonar;
    }

    @Override
    double getSensorReading() {
        return sonar.getRangeInches();
    }
}
