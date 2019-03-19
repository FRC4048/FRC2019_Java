/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.utils.SmartShuffleboard;
import org.usfirst.frc4048.utils.diagnostics.DiagSwitch;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class GamePieceMode extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private DigitalInput cargoSensor;
  private boolean gamePieceState;
  private boolean overrideSensor;

  public GamePieceMode() {
    cargoSensor = new DigitalInput(RobotMap.CARGO_OPTICAL_SENSOR_ID);
    Robot.diagnostics.addDiagnosable(new DiagSwitch("Cargo Optical Sensor", cargoSensor));
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void periodic() {
    if (!overrideSensor){
      setStateBasedOnSensor();
    }
    SmartShuffleboard.put("Driver", "Is Cargo?", isCargo());
  }
  public void toggleState() {
    if (gamePieceState) {
      gamePieceState = false;
    } else {
      gamePieceState = true;
    }
  }

  public void setStateBasedOnSensor() {
    if (!cargoSensor.get()) { //we are assuming when a cargo is in the sensor returns false 
      gamePieceState = true;
    } else {
      gamePieceState = false;
    }
  }

  public boolean isCargo() {
    return gamePieceState;
  }

  public void setOverrideSensor(boolean isCargo) {
    gamePieceState = isCargo;
  }
}
