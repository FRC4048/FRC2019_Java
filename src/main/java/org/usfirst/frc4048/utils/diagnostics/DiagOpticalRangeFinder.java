package org.usfirst.frc4048.utils.diagnostics;

import org.usfirst.frc4048.utils.OpticalRangeFinder;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DiagOpticalRangeFinder implements Diagnosable {

    private String name;
    private OpticalRangeFinder opticalRangeFinder;
    private NetworkTableEntry networkTableEntry;

    private boolean seenMaxDistance;
    private boolean seenMinDistance;

    /**
     * Constructor
     * 
     * @param name                    -Name of the sensor, to be used on Shuffleboard
     * @param opticalRangeFinder      -The OpticalRangeFinder object to be tested.
     * @param shuffleboardTab         -The Shuffleboard tab to add the tile to.
     */
    public DiagOpticalRangeFinder(String name, OpticalRangeFinder opticalRangeFinder, ShuffleboardTab shuffleboardTab){
        this.name = name;
        this.opticalRangeFinder = opticalRangeFinder;

        this.networkTableEntry = shuffleboardTab.add(name, false).getEntry();
        reset();
    }

    @Override
    public void refresh() {
        double distance = opticalRangeFinder.getDistanceInInches();
        if (distance < 3.0){
            seenMinDistance = true;
        } else if (distance > 10.0){
            seenMaxDistance = true;
        }

        networkTableEntry.setBoolean(seenMaxDistance && seenMinDistance);
    }

    @Override
    public void reset() {
        seenMaxDistance = seenMinDistance = false;
    }
}
