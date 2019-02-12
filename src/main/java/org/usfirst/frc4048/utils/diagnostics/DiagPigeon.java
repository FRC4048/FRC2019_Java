package org.usfirst.frc4048.utils.diagnostics;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DiagPigeon extends DiagDistanceTraveled {

    private PigeonIMU pigeon;

    public DiagPigeon(String name, int requiredTravel, PigeonIMU pigeon, ShuffleboardTab shuffleboardTab) {
        super(name, requiredTravel, shuffleboardTab);
        this.pigeon = pigeon;

        reset();
    }

    public DiagPigeon(String name, int requiredTravel, PigeonIMU pigeon, boolean DO_NOT_USE_FOR_TESTING_ONLY) {
        super(name, requiredTravel, DO_NOT_USE_FOR_TESTING_ONLY);
        this.pigeon = pigeon;

        reset();
    }

    @Override
    protected int getCurrentValue() {
        return (int)pigeon.getFusedHeading();
    }
}
