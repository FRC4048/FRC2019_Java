/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.manipulator.hatchpanel;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;

public class HatchPanelIntake extends LoggedCommand {
  
  /**
   * true is extend false is retract
   * 
   */
  public HatchPanelIntake() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    super("ManualHatchPanelIntake");
    requires(Robot.hatchPanelSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    if(!Robot.gamePieceMode.isCargo()){
      Robot.hatchPanelSubsystem.extendPiston(); 
    }


  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
   


  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void loggedInterrupted() {
    end(); 
  }

  @Override
  protected void loggedCancel() {

  }
}
