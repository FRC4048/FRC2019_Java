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

public class LockPivot extends LoggedCommand {
  private boolean lock;
  public LockPivot(boolean lock) {
    super("LockPivot");
    this.lock = lock;
    requires(Robot.pivot);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    Robot.pivot.movePiston(lock);
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

  @Override
  protected void loggedInterrupted() {

  }

  @Override
  protected void loggedCancel() {

  }
}
