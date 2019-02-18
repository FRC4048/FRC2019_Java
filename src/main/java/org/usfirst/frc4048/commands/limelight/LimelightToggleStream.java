/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.limelight;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.subsystems.DrivetrainSensors;
import org.usfirst.frc4048.utils.LimeLightVision;

import edu.wpi.first.wpilibj.command.Command;

public class LimelightToggleStream extends LoggedCommand {

  public LimelightToggleStream() {
    super(String.format(" is running"));
    setRunWhenDisabled(true);
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
    Robot.drivetrainSensors.setStream((Robot.drivetrainSensors.getStream() + 1) % 3);
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
    loggedEnd();
  }

  @Override
  protected void loggedCancel() {
    loggedEnd();
  }
}
