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
    CARGO_CARGOSHIP_POS(2615.0), CARGO_INTAKE_POS(2615.0), HATCH_ROCKET_BOT(0.0), HATCH_ROCKET_MID(5625.0), HATCH_ROCKET_HIGH(10568.0), CARGO_ROCKET_LOW(0.0), CARGO_ROCKET_MID(5222.0), CARGO_ROCKET_HIGH(10588.0), SAFE_PIVOT_POS(3750);//The 3750 is an esimate based off of 250 ticks per inch * 15 inches.

    public double position;

    ElevatorPosition(double position) {
        this.position = position;
    }
    
    public double getPosition() {
        return position;
    }
}
