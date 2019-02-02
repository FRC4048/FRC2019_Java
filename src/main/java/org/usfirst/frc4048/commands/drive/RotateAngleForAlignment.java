/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.drive;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class RotateAngleForAlignment extends LoggedCommand {
  private final double rightRocketSideAngle = 90.0;
  private final double rightRocketBackAngle = 151.25;
  private final double rightRocketFrontAngle = 61.25;
  private final double leftRocketSideAngle = 270;
  private final double leftRocketBackAngle = 241.25;
  private final double leftRocketFrontAngle = 331.25;
  private final double cargoFrontAngle = 0.0;
  private final double[] depositAngles = new double[]{rightRocketSideAngle, rightRocketBackAngle, rightRocketFrontAngle, 
          leftRocketSideAngle, leftRocketBackAngle,  leftRocketFrontAngle, cargoFrontAngle};

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
    super(String.format(" is running"));
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
    double angleToMoveTo = calculateAngle(Robot.drivetrain.getGyro());
    Scheduler.getInstance().add(new RotateAngle(angleToMoveTo));
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

  public double calculateAngle(double currAngle) {
    double currentDistance = 0;
    double closestDistance= 360;
    int closestIndex = 0;

    currAngle = currAngle % 360;
    if(currAngle < 0) {
      currAngle += 360;
    }

    for (int i = 0; i < depositAngles.length; i++)
    {
      if (findAngleDistance(currAngle, depositAngles[i]) < closestDistance)
      currentDistance = findAngleDistance(currAngle, depositAngles[i]);
      if (currentDistance < closestDistance)
      {
        closestDistance = currentDistance;
        closestIndex = i;
      }
    }

    return depositAngles[closestIndex];
  }

  private double findAngleDistance(double angle1, double angle2) {
    double diff;

    diff = Math.abs(angle1-angle2);
    if (diff > 180)
    {
      diff = Math.abs(diff - 360);
    }
    return diff;
  }

  @Override
  protected void loggedCancel() {
    loggedEnd();
  }
}
