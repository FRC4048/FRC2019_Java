/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;


/**
 * Add your docs here.
 */
public class HatchPanelSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private DigitalInput limitSwitchLeft; 
  private DigitalInput limitSwitchRight; 
  private Solenoid hatchPanelPiston;
  public HatchPanelSubsystem() {

      limitSwitchLeft = new DigitalInput(RobotMap.CARGO_LIMIT_SWITCH_LEFT_ID);
      limitSwitchRight = new DigitalInput(RobotMap.CARGO_LIMIT_SWITCH_RIGHT_ID);
      hatchPanelPiston = new Solenoid(RobotMap.HATCH_PANEL_PISTON_ID);
      }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    // This is a default command you always want to be done
  }

public boolean getLeftLimit(){
  
  return limitSwitchLeft.get();

}

public boolean getRightLimit(){
  
  return limitSwitchRight.get();

}
  public void extendPiston() {
    hatchPanelPiston.set(true);
  }

  public void retractPiston() {
    hatchPanelPiston.set(false);
  } 

  public boolean checkPiston(){

    return hatchPanelPiston.get(); 
  }

}
