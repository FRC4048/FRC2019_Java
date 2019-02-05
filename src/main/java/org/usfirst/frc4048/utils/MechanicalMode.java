/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.utils;

import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class MechanicalMode {
    
    private DigitalInput cargoInput;
    private DigitalInput hatchInput;
    

    public MechanicalMode() {
        hatchInput = new DigitalInput(RobotMap.HATCH_DIGITAL_INPUT_ID);
        cargoInput = new DigitalInput(RobotMap.CARGO_DIGITAL_INPUT_ID);
    }

    public int getMode() {
        SmartDashboard.putBoolean("Hatch DIO", hatchInput.get());
        SmartDashboard.putBoolean("Cargo DIO", cargoInput.get());
        if(hatchInput.get() && !cargoInput.get()) {
            return RobotMap.HATCH_RETURN_CODE;
        } else if(!hatchInput.get() && cargoInput.get()) { 
            return RobotMap.CARGO_RETURN_CODE;
        } else {
            return RobotMap.NOTHING_RETURN_CODE;
        }
    }
}
