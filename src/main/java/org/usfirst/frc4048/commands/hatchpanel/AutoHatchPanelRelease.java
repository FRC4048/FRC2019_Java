/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.hatchpanel;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoHatchPanelRelease extends Command {
  
  /**
   * true is extend false is retract
   * 
   */
  public AutoHatchPanelRelease() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

    requires(Robot.hatchPanelSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

 

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
   
    if(Robot.hatchPanelSubsystem.getLeftLimit() || Robot.hatchPanelSubsystem.getRightLimit()){

      Robot.hatchPanelSubsystem.retractPiston(); 
   }


  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !Robot.hatchPanelSubsystem.checkPiston(); 
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end(); 
  }
}
