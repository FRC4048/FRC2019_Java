/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.manipulator.cargo;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;

public class CargoWristDown extends LoggedCommand {
  public CargoWristDown() {
    super("CargoWristDown");
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.cargoSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    Robot.cargoSubsystem.setPiston(true);
    setTimeout(2);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
    Robot.cargoSubsystem.setPiston(false);
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
