package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DiagSonar implements Diagnosable {

    private String name;
    private Ultrasonic sonar;
    private NetworkTableEntry networkTableEntry;

    private boolean seenMaxDistance;
    private boolean seenMinDistance;

    /**
     * Constructor
     * 
     * @param name                    -Name of the sensor, to be used on Shuffleboard
     * @param sonar                   -The Ultrasonic object to be tested.
     * @param shuffleboardTab         -The Shuffleboard tab to add the tile to.
     */
    public DiagSonar(String name, Ultrasonic sonar, ShuffleboardTab shuffleboardTab){
        this.name = name;
        this.sonar = sonar;

        this.networkTableEntry = shuffleboardTab.add(name, false).getEntry();
        reset();
    }

    @Override
    public void refresh() {
        double distance = sonar.getRangeInches();
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
