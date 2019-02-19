/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.utils;

import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class allows you to get the mode that the robot is in (Hatch, or Cargo). It does this based
 * off of the DIO ports that check which mechanism is plugged in.
 */
public class MechanicalMode {
    
    private DigitalInput cargoInput;
    private DigitalInput hatchInput;
    

    public MechanicalMode() {
        hatchInput = new DigitalInput(RobotMap.HATCH_DIGITAL_INPUT_ID);
        cargoInput = new DigitalInput(RobotMap.CARGO_DIGITAL_INPUT_ID);
    }

    public int getMode() {
        if(!hatchInput.get() && cargoInput.get()) {
            SmartShuffleboard.put("Driver", "Mechanical Mode", "HATCH");
            return RobotMap.HATCH_RETURN_CODE;
        } else if(hatchInput.get() && !cargoInput.get()) { 
            SmartShuffleboard.put("Driver", "Mechanical Mode", "CARGO");
            return RobotMap.CARGO_RETURN_CODE;
        } else {
            DriverStation.reportError("-----Unable to determine robot has the Hatch Panel or Cargo assembly mounted-----", true);
            DriverStation.reportError("-----DEFAULTED TO HATCH  SUBSYSTEM-----", true);
            SmartShuffleboard.put("Driver", "ERROR", "COULD NOT DETERMINE THE MECHANISM ATTACHED DEFAULTED TO HATCH");
            return RobotMap.HATCH_RETURN_CODE;
        }
    }
}
