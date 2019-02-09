package org.usfirst.frc4048.utils.diagnostics;

import org.usfirst.frc4048.utils.OpticalRangeFinder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Diagnostics class for the Optical range finder. It is a DiagMinMax object.
 * Give it a max and minimum distance to test.
 */
public class DiagOpticalRangeFinder extends DiagMinMax {

    private OpticalRangeFinder opticalRangeFinder;


    /**
     * Constructor
     * 
     * @param name                    -Name of the sensor, to be used on Shuffleboard
     * @param opticalRangeFinder      -The OpticalRangeFinder object to be tested.
     * @param shuffleboardTab         -The Shuffleboard tab to add the tile to.
     * @param minDistance             -The minimum testing distance for the optical range finder; the one that will be tested against.
     * @param maxDistance             -The maximum testing distance for the optical range finder; the one that will be tested against.
     */
    public DiagOpticalRangeFinder(String name, OpticalRangeFinder opticalRangeFinder, ShuffleboardTab shuffleboardTab, double minDistance, double maxDistance){
        super(name, minDistance, maxDistance, shuffleboardTab);
        this.opticalRangeFinder = opticalRangeFinder;
    }

    /**
     * Constructor
     * DO NOT USE THIS CONSTRUCTOR; IT IS FOR TESTING PURPOSES ONLY!
     */
    public DiagOpticalRangeFinder(String name, OpticalRangeFinder opticalRangeFinder, boolean DO_NOT_USE_TESTING_ONLY, double minDistance, double maxDistance){
        super(name, minDistance, maxDistance, DO_NOT_USE_TESTING_ONLY);
        this.opticalRangeFinder = opticalRangeFinder;
    }

    @Override
    double getSensorReading() {
        return opticalRangeFinder.getDistanceInInches();
    }
}
