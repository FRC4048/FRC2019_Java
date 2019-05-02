/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.utils;

import org.usfirst.frc4048.subsystems.Elevator;

/**
 * Add your docs here.
 */
public enum ElevatorPosition {
    CARGO_CARGOSHIP_POS(3630.0), 
    CARGO_INTAKE_POS(2615.0), 
    HATCH_ROCKET_BOT(0.0), 
    HATCH_ROCKET_MID(6400.0), 
    HATCH_ROCKET_HIGH(10688.0), 
    CARGO_ROCKET_LOW(0.0), 
    CARGO_ROCKET_MID(5700.0), 
    CARGO_ROCKET_HIGH(10688.0), 
    SAFE_HEIGHT(1050.0);

    public double position;

    ElevatorPosition(double position) {
        this.position = position;
    }
    
    public double getPosition() {
        return position;
    }
}
