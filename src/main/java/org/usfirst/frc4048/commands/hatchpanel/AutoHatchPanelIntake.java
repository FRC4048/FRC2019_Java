/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.hatchpanel;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;

public class AutoHatchPanelIntake extends LoggedCommand {
  
  /**
   * true is extend false is retract
   * 
   */
  public AutoHatchPanelIntake() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    super("AutoHatchPanelIntake");

    requires(Robot.hatchPanelSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
   
    if(Robot.hatchPanelSubsystem.getLeftLimit() || Robot.hatchPanelSubsystem.getRightLimit()){

      Robot.hatchPanelSubsystem.extendPiston(); 
   }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return Robot.hatchPanelSubsystem.checkPiston(); 
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
