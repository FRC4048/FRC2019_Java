/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.utils;

/**
 * Add your docs here.
 */
public enum ElevatorPosition {
    CARGO_CARGOSHIP_POS(10405.0), CARGO_INTAKE_POS(300.0), HATCH_ROCKET_BOT(0.0), HATCH_ROCKET_MID(10440.0), HATCH_ROCKET_HIGH(19580.0), CARGO_ROCKET_LOW(7300.0), CARGO_ROCKET_MID(15585.0), CARGO_ROCKET_HIGH(24325.0), START_MATCH_POS(3750);//The 3750 is an esimate based off of 250 ticks per inch * 15 inches.

    public double position;

    ElevatorPosition(double position) {
        this.position = position;
    }
    
    public double getPosition() {
        return position;
    }
}
