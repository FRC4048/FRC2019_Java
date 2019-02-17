package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import org.usfirst.frc4048.swerve.drive.CanTalonSwerveEnclosure;

public class DiagSwerveEnclosureTalonSRX extends DiagDistanceTraveled {
    private CanTalonSwerveEnclosure enclosure;

    public DiagSwerveEnclosureTalonSRX(String name, int requiredTravel, CanTalonSwerveEnclosure enclosure) {
        super(name, requiredTravel);
        this.enclosure = enclosure;
    }

    @Override
    protected int getCurrentValue() {
        return enclosure.getLastEncPosition();
    }
}
