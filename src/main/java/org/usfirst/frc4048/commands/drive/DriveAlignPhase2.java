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
import org.usfirst.frc4048.utils.CameraDistance;
import org.usfirst.frc4048.utils.SmartShuffleboard;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveAlignPhase2 extends LoggedCommand {
  private double angle;
  private double moveDistance;
  private boolean done;
  private double pMin;
  private double pMax;
  private CentricMode mode;
  private double initialDrivetrainDistance;
  private double initialAngle;
  private double time;

  private final double PID_DISTANCE = 40.0; // Distance, in Inches, for the PID portion of the path
  private final double TIMEOUT_DISTANCE = 15.0; // Distance, in Inches, for the portion of the path where timeout is
                                                // calculated
  private final double MIN_ANGLE_ERROR = 2.0; // The angle error that triggers correction
  private final double ROTATION_SPEED = 0.05; // The rotation correction power
  private final double DISTANCE_AWAY = 20.0; //The distance away from the target where we stop moving
  public DriveAlignPhase2(double pMin, double pMax, boolean isCamFront) {
    super(String.format(" is running, pMin=%f, pMax=%f, isCamFront=%b", pMin, pMax, isCamFront));
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
    this.pMin = pMin;
    this.pMax = pMax;
    
    // we do this because if the camera is in the back we need to move backwards
    if (!isCamFront) {
      this.pMax *= -1;
      this.pMin *= -1;
    }
    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "pMax", pMax);
    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "pMin", pMin);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    double forward = 0;
    double horizontal = 0;
    
    CameraDistance targetDistance = Robot.drivetrainSensors.getTargetDistance();
    if (targetDistance == null) {
      angle = 0;
      moveDistance = 0;
      System.out.println("----------------------No target detected----------------------");
      done = true;
    } else {
      forward = targetDistance.getForward();
      horizontal = targetDistance.getSideways();
      angle = Math.atan(horizontal / (forward - DISTANCE_AWAY));
      moveDistance = Math.sqrt(Math.pow((forward - DISTANCE_AWAY), 2) + Math.pow(horizontal, 2));
      mode = Robot.drivetrain.swerveDrivetrain.getModeRobot();
      Robot.drivetrain.swerveDrivetrain.setModeRobot();

      initialDrivetrainDistance = Robot.drivetrain.getDistance();
      initialAngle = Robot.drivetrain.getGyro();
      done = false;
      time = 0;

    }

    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "Forward Distance", forward);
    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "Horizontal Distance", horizontal);
    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "Angle", Math.toDegrees(angle));
    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "Move Distance", moveDistance);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    // if the timer is started and the timer is more than 2 seconds then we are
    // travelling 15 inches
    // for more than 2 seconds and we stop the command
    if (time != 0 && Timer.getFPGATimestamp() - time > 2.0) {
      done = true;
      return;
    }

    double traveledDistance = Math.abs(Robot.drivetrain.getDistance() - initialDrivetrainDistance);
    double distanceLeft = moveDistance - traveledDistance;
    // SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "Distance Left", distanceLeft);
    if (distanceLeft <= 0) {
      done = true;
      return;
    }

    // if the distance left is less than 15 and the timer has not started then it
    // starts the timer.
    if ((time == 0) && (distanceLeft <= TIMEOUT_DISTANCE)) {
      time = Timer.getFPGATimestamp();
    }

    double power = pidCalc(distanceLeft, pMax, pMin);
    double pFwd = power * Math.cos(angle);
    double pDir = power * Math.sin(angle);

    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "power", power);
    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "pFwd", pFwd);
    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "pDir", pDir);
    double currAngle = Robot.drivetrain.getGyro();
    double rot = calcRot(initialAngle, currAngle);

    Robot.drivetrain.move(pFwd, pDir, rot);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return done;
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
    if (mode.equals(CentricMode.FIELD)) {
      Robot.drivetrain.swerveDrivetrain.setModeField();
    } else {
      Robot.drivetrain.swerveDrivetrain.setModeRobot();
    }
    Robot.drivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void loggedInterrupted() {
    loggedEnd();
  }

  private double calcRot(double startAngle, double currAngle) {
    if (Math.abs(startAngle - currAngle) < MIN_ANGLE_ERROR) {
      return 0;
    }
    // The Math.signum returns -1, 0, 1. So if current angle is larger than the
    // starting angle
    // then we need to turn counter clockwise. If the starting angle is larger than
    // the current angle
    // we need to turn clockwise. The rotation value currently is hardcoded at 0.1.
    return Math.signum(startAngle - currAngle) * ROTATION_SPEED;
  }

  /**
   * Calculate the power to drive based off of the remaining distance
   * 
   * @param distanceLeft distance (in Inch) left to travel
   * @param pMax         the maximum power to drive at
   * @param pMin         the minimum power to drive at
   * @return the power to drive at (between 0-1)
   */
  private double pidCalc(double distanceLeft, double pMax, double pMin) {
    if (distanceLeft > PID_DISTANCE) {
      return pMax;
    }
    double pidRatio = distanceLeft / PID_DISTANCE;
    SmartShuffleboard.put("DrivetrainSensors", "drive-align-phase-2", "pidRatio", pidRatio);
    if (Math.abs(pidRatio * pMax) < Math.abs(pMin)) {
      return pMin;
    } else {
      return pidRatio * pMax;
    }
  }

  @Override
  protected void loggedCancel() {
    loggedEnd();
  }
}
