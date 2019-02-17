/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.drive;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.swerve.math.CentricMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class CentricModeToggle extends LoggedCommand {
  private boolean done;

  public CentricModeToggle() {
    super("CentricModeToggle");
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    done = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    CentricMode mode = Robot.drivetrain.swerveDrivetrain.getModeRobot();
    if (DriverStation.getInstance().isAutonomous()) {
      Robot.drivetrain.swerveDrivetrain.setModeRobot();
      Robot.drivetrain.setScaleSpeed(); 
      done = true;
    }
    if (!done) {
      if (mode == CentricMode.FIELD) {
        Robot.drivetrain.swerveDrivetrain.setModeRobot();
        done = true;
      } else {
        Robot.drivetrain.swerveDrivetrain.setModeField();
        done = true;
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return done;
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void loggedInterrupted() {
  }

  @Override
  protected void loggedCancel() {

  }
}
