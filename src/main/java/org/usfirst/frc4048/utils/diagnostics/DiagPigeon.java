package org.usfirst.frc4048.utils.diagnostics;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DiagPigeon extends DiagDistanceTraveled {

    private PigeonIMU pigeon;

    public DiagPigeon(String name, int requiredTravel, PigeonIMU pigeon) {
        super(name, requiredTravel);
        this.pigeon = pigeon;

        reset();
    }

    @Override
    protected int getCurrentValue() {
        return (int)pigeon.getFusedHeading();
    }
}
