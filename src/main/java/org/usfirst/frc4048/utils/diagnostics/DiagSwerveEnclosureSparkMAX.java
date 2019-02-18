package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import org.usfirst.frc4048.swerve.drive.SparkMAXSwerveEnclosure;

public class DiagSwerveEnclosureSparkMAX extends DiagDistanceTraveled {
    private SparkMAXSwerveEnclosure enclosure;

    public DiagSwerveEnclosureSparkMAX(String name, int requiredTravel, SparkMAXSwerveEnclosure enclosure) {
        super(name, requiredTravel);
        this.enclosure = enclosure;
    }

    @Override
    protected int getCurrentValue() {
        return enclosure.getLastEncPosition();
    }
}
