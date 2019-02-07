package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DiagSonar implements Diagnosable {

    private String name;
    private Ultrasonic sonar;
    private NetworkTableEntry networkTableEntry;
    private double maxDistance;
    private double minDistance;

    private boolean seenMaxDistance;
    private boolean seenMinDistance;

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
        this.name = name;
        this.sonar = sonar;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;

        this.networkTableEntry = shuffleboardTab.add(name, false).getEntry();
        reset();
    }

    /**
     * Constructor
     * DO NOT USE THIS; THIS IS FOR TESTING PURPOSES ONLY
     * 
     * @param name                    -Name of the sensor, to be used on Shuffleboard
     * @param sonar                   -The Ultrasonic object to be tested.
     * @param DO_NOT_USE_TESTING_ONLY -THIS CONSTRUCTOR IS ONLY FOR TESTING DO NOT USE THIS!
     * @param minDistance             -The minimum testing distance for the sonar; the one that will be tested against.
     * @param maxDistance             -The maximum testing distance for the sonar; the one that will be tested against.
     */
    public DiagSonar(String name, Ultrasonic sonar, boolean DO_NOT_USE_TESTING_ONLY, double minDistance, double maxDistance){
        this.name = name;
        this.sonar = sonar;
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
        double distance = sonar.getRangeInches();
        if (distance < minDistance){
            seenMinDistance = true;
        } else if (distance > maxDistance){
            seenMaxDistance = true;
        }

        return seenMaxDistance && seenMinDistance;
    }
}
