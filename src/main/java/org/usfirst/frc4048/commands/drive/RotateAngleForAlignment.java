/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.drive;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class RotateAngleForAlignment extends LoggedCommand {
  private static final double rightRocketSideAngle = 90.0;
  private static final double rightRocketBackAngle = 151.25;
  private static final double rightRocketFrontAngle = 28.75;
  private static final double leftRocketSideAngle = 270;
  private static final double leftRocketBackAngle = 208.75;
  private static final double leftRocketFrontAngle = 331.25;
  private static final double cargoFrontAngle = 0.0;
  private static final double loadingStationAngle = 180.0;
  private static final double[] hatchDepositAngles = new double[]{rightRocketSideAngle, rightRocketBackAngle, rightRocketFrontAngle, 
          leftRocketSideAngle, leftRocketBackAngle,  leftRocketFrontAngle, cargoFrontAngle, loadingStationAngle};
  private static final double[] cargoDepositAngles = new double[]{rightRocketSideAngle, leftRocketSideAngle, 
          cargoFrontAngle, loadingStationAngle};

  /*
  \  <-- Back Angle
   \
    |
    | <-- Side Angle
   /
  / <-- Front Angle 
  */

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
    double angleToMoveTo = calculateAngle(Robot.drivetrain.getGyro(), Robot.mechanicalMode.getMode());
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


  public double calculateAngle(double currAngle, int mode) {
    double currentDistance = 0;
    double closestDistance= 360;
    int closestIndex = 0;
    double[] currentDepositAngles;

    if (mode == RobotMap.CARGO_RETURN_CODE) {
      currentDepositAngles = cargoDepositAngles;
    }
    else if (mode == RobotMap.HATCH_RETURN_CODE) {
      currentDepositAngles = hatchDepositAngles;
    }
    else {
      return 0;
    }

    /* Make sure the angle is positive and less tha 360 */
    currAngle = currAngle % 360;
    if(currAngle < 0) {
      currAngle += 360;
    }

    /* find the angle that is closest to the current robot angle */
    for (int i = 0; i < currentDepositAngles.length; i++)
    {
      currentDistance = findAngleDistance(currAngle, currentDepositAngles[i]);
      if (currentDistance < closestDistance)
      {
        closestDistance = currentDistance;
        closestIndex = i;
      }
    }
    return currentDepositAngles[closestIndex];
  }

  
  private double findAngleDistance(double angle1, double angle2) {
    double diff;

    /* every 2 points on the circle have 2 'distances' from each other. A cw distance and a ccw distance  */
    /* One of the distances is >= 180, the other is <=180, and they add up to 360.                        */
    /* We want to make sure that if we get the >180, we actually return the other distance.               */
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
