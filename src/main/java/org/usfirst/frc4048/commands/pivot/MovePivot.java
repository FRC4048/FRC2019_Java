/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.pivot;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.subsystems.Pivot;
import org.usfirst.frc4048.utils.SmartShuffleboard;

public class MovePivot extends LoggedCommand {
  private final double PIVOT_SPEED = 1.0;
  private boolean startingState;
  public MovePivot() {
    super("MovePivot");
    // Use requires() here to declare subsystem dependencies
    requires(Robot.pivot);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    startingState = Robot.pivot.getState();
    Robot.pivot.movePiston(false);//Unlock pivot at beginning of command
    setTimeout(6);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    if (Robot.pivot.getState()){
      // if (Robot.pivot.getDeployedSwitch()){
      //   Robot.pivot.setSpeed(0);
      // }
      // else {
      //   Robot.pivot.setSpeed(PIVOT_SPEED);
      // }
      Robot.pivot.setSpeed(-PIVOT_SPEED);
    } else {
      // if (Robot.pivot.getRetractedSwitch()){
      //   Robot.pivot.setSpeed(0);

      // } else {
      //   Robot.pivot.setSpeed(-PIVOT_SPEED);
      // }
      Robot.pivot.setSpeed(PIVOT_SPEED);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    if(startingState) {
      return Robot.pivot.getRetractedSwitch() || isTimedOut();
    } else {
      return Robot.pivot.getDeployedSwitch() || isTimedOut();
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
    Robot.pivot.setSpeed(0.0);
    Robot.pivot.movePiston(true);//Lock pivot in place when done
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void loggedInterrupted() {
    loggedEnd();
  }

  @Override
  protected void loggedCancel() {
    loggedEnd();
  }
}
