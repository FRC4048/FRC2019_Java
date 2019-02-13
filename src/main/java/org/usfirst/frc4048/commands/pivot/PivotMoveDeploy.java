/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.pivot;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;

public class PivotMoveDeploy extends LoggedCommand {
  private static final double OUT_SPEED = 1;
  public PivotMoveDeploy() {
    super("PivotMoveDeploy");
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.pivot);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    setTimeout(3);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
  
    Robot.pivot.setSpeed(OUT_SPEED);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return Robot.pivot.getLeftSwitch() || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
    if(Robot.pivot.getLeftSwitch()){
      Robot.pivot.movePiston(true);
    }
    Robot.pivot.setSpeed(0.0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void loggedInterrupted() {
    loggedEnd();
  }

  @Override
  protected void loggedCancel() {

  }
}
