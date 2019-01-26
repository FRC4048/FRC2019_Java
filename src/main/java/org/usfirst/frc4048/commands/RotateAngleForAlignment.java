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
  private final double angleMarginValue = 20.0;
  private final double rightRocketSideAngle = 90.0;
  private final double rightRocketBackAngle = 151.25;
  private final double rightRocketFrontAngle = 61.25;
  private final double leftRocketSideAngle = 270;
  private final double leftRocketBackAngle = 241.25;
  private final double leftRocketFrontAngle = 331.25;
  private final double cargoFrontAngle = 0.0;

  /*
  \  <-- Back Angle
   \
    |
    | <-- Side Angle
   /
  / <-- Front Angle 
  */

  //There is a problem with overlap because if you are in the middle of 2 angles it will prefer one angle
  //to another because of the order of the if statements 

  public RotateAngleForAlignment() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    angle = Robot.drivetrain.getGyro();
    angle = angle % 360;
    if(angle < 0) {
      angle += 360;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double angleToMoveTo = calculateAngle(angle);

    

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

  public double calculateAngle(double currAngle) {
    double resultAngle = 0;

    if(currAngle <= rightRocketSideAngle + angleMarginValue && currAngle >= rightRocketSideAngle - angleMarginValue) {
      resultAngle = rightRocketSideAngle;
    } else if(currAngle <= rightRocketBackAngle + angleMarginValue && currAngle >= rightRocketBackAngle - angleMarginValue) { 
      resultAngle = rightRocketBackAngle;
    } else if(currAngle <= rightRocketFrontAngle + angleMarginValue && currAngle >= rightRocketFrontAngle - angleMarginValue) { 
      resultAngle = rightRocketFrontAngle;
    } else if (currAngle <= leftRocketSideAngle + angleMarginValue && currAngle >= leftRocketSideAngle - angleMarginValue) {
      resultAngle = leftRocketSideAngle;
    } else if(currAngle <= leftRocketBackAngle + angleMarginValue && currAngle >= leftRocketBackAngle - angleMarginValue) { 
      resultAngle = leftRocketBackAngle;
    } else if(currAngle <= leftRocketFrontAngle + angleMarginValue && currAngle >= leftRocketFrontAngle - angleMarginValue) { 
      resultAngle = leftRocketFrontAngle;
    } else if(currAngle >= 360 - angleMarginValue || currAngle < angleMarginValue) {
      resultAngle = cargoFrontAngle;
    } else {
      return currAngle;
    }
    
    //If the angle given at the beginning is negitive then we are at the left side rocket or right side of cargo
    // if(currAngle > 180 && resultAngle != cargoFrontAngle) {
    //   resultAngle -= 180;
    // }

    return resultAngle;
  }
}
