package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import org.usfirst.frc4048.swerve.drive.CanTalonSwerveEnclosure;

public class DiagSwerveEnclosure extends DiagDistanceTraveled {
    private CanTalonSwerveEnclosure enclosure;

    public DiagSwerveEnclosure(String name, int requiredTravel, CanTalonSwerveEnclosure enclosure, ShuffleboardTab shuffleboardTab) {
        super(name, requiredTravel, shuffleboardTab);
        this.enclosure = enclosure;
    }

    public DiagSwerveEnclosure(String name, int requiredTravel, CanTalonSwerveEnclosure enclosure, boolean DO_NOT_USE_FOR_TESTING_ONLY) {
        super(name, requiredTravel, DO_NOT_USE_FOR_TESTING_ONLY);
        this.enclosure = enclosure;
    }

    @Override
    protected int getCurrentValue() {
        return enclosure.getEncPosition();
    }
}
