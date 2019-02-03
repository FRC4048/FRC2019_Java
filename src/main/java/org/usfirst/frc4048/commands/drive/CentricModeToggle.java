/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.drive;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.swerve.math.CentricMode;

import edu.wpi.first.wpilibj.command.Command;

public class CentricModeToggle extends Command {
  CentricMode mode;
  public CentricModeToggle() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mode = Robot.drivetrain.swerveDrivetrain.getModeRobot();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(mode == CentricMode.FIELD) {
      Robot.drivetrain.swerveDrivetrain.setModeRobot();
    } else {
      Robot.drivetrain.swerveDrivetrain.setModeField();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
