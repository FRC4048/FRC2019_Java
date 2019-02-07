package org.usfirst.frc4048.utils.diagnostics;

import org.usfirst.frc4048.utils.OpticalRangeFinder;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DiagOpticalRangeFinder implements Diagnosable {

    private String name;
    private OpticalRangeFinder opticalRangeFinder;
    private NetworkTableEntry networkTableEntry;
    private double minDistance;
    private double maxDistance;

    private boolean seenMaxDistance;
    private boolean seenMinDistance;

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
        this.name = name;
        this.opticalRangeFinder = opticalRangeFinder;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;

        this.networkTableEntry = shuffleboardTab.add(name, false).getEntry();
        reset();
    }

    /**
     * Constructor
     * DO NOT USE THIS CONSTRUCTOR; IT IS FOR TESTING PURPOSES ONLY!
     * 
     * @param name                    -Name of the sensor, to be used on Shuffleboard
     * @param opticalRangeFinder      -The OpticalRangeFinder object to be tested.
     * @param DO_NOT_USE_TESTING_ONLY -DO NOT USE THIS IT IS FOR TESTING ONLY!
     * @param minDistance             -The minimum testing distance for the optical range finder; the one that will be tested against.
     * @param maxDistance             -The maximum testing distance for the optical range finder; the one that will be tested against.
     */
    public DiagOpticalRangeFinder(String name, OpticalRangeFinder opticalRangeFinder, boolean DO_NOT_USE_TESTING_ONLY, double minDistance, double maxDistance){
        this.name = name;
        this.opticalRangeFinder = opticalRangeFinder;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;

        reset();
    }



    @Override
    public void refresh() {
        

        networkTableEntry.setBoolean(getDiagResult());
    }

    @Override
    public void reset() {
        seenMaxDistance = seenMinDistance = false;
    }

    //Package protected
    boolean getDiagResult(){
        double distance = opticalRangeFinder.getDistanceInInches();
        if (distance < minDistance){
            seenMinDistance = true;
        } else if (distance > maxDistance){
            seenMaxDistance = true;
        }

        return seenMaxDistance && seenMinDistance;
    }
}
