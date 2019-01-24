/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class RotateAngleForAlignment extends Command {
  private double angle;
  private double angleToMoveTo;
  private final double angleMarginValue = 10.0;
  private final double rocketSideAngle = 90.0;
  private final double rocketBackAngle = 151.25;
  private final double rocketFrontAngle = 61.25;
  private final double cargoFrontAngle = 0.0;

  /*
  \  <-- Back Angle
   \
    |
    | <-- Side Angle
   /
  / <-- Front Angle 
  */

  public RotateAngleForAlignment() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    angle = Robot.drivetrain.getGyro();
    angleToMoveTo = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Math.abs(angle) <= rocketSideAngle + angleMarginValue && Math.abs(angle) >= rocketSideAngle - angleMarginValue) {
      angleToMoveTo = rocketSideAngle;
    } else if(Math.abs(angle) <= rocketBackAngle + angleMarginValue && Math.abs(angle) >= rocketBackAngle - angleMarginValue) { 
      angleToMoveTo = rocketBackAngle;
    } else if(Math.abs(angle) <= rocketFrontAngle + angleMarginValue && Math.abs(angle) >= rocketFrontAngle - angleMarginValue) { 
      angleToMoveTo = rocketFrontAngle;
    } else if(Math.abs(angle) <= cargoFrontAngle + angleMarginValue && Math.abs(angle) >= cargoFrontAngle - angleMarginValue) {
      angleToMoveTo = cargoFrontAngle;
    }

    //If the angle given at the beginning is negitive then we are at the left side rocket or right side of cargo
    if(angle < 0 && angleToMoveTo != cargoFrontAngle) {
      angleToMoveTo *= -1;
    }

    Scheduler.getInstance().add(new RotateAngle(angleToMoveTo));  
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
