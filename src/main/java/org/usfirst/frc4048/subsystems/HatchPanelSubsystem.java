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
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.diagnostics.DiagSwitch;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;


/**
 * Add your docs here.
 */
public class HatchPanelSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Solenoid hatchPanelPiston;
  public HatchPanelSubsystem() {

    hatchPanelPiston = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.HATCH_PANEL_PISTON_ID);
  
  }

  public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(this.getClass()) {
    
    protected void addAll() {
      add("Check Piston", checkPiston());
    }
  };

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    // This is a default command you always want to be done
  }
  
  @Override
  public void periodic() {
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
