/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.swerve.math.CentricMode;
import org.usfirst.frc4048.utils.CameraDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightAlign extends CommandGroup {
  private double distance;
  private double horizontal;
  private double horizontalSpeed;
  private double distanceSpeed;
  private double diagonalDistance;
  private double angle = 0.0;
  private final double minDistance = 20;
  private final double ANGLE = 0.0;
  private final double MAX_SPEED = 0.4;
  private final double MIN_SPEED = 0.3;
  private double speed = 0.4;
  CentricMode mode;

  public LimelightAlign() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);

    CameraDistance cameraDistance = Robot.limelight.getTargetDistance();
    if (cameraDistance == null) {
      return;
    }
    distance = cameraDistance.getForward();
    horizontal = cameraDistance.getSideways();
  
    diagonalDistance = Math.sqrt((horizontal*horizontal)+(distance*distance));
  
    horizontalSpeed = (horizontal/distance) * speed;
    horizontalSpeed *= -1; 
    distanceSpeed = speed;
    addSequential(new DriveDistanceMaintainAngle(diagonalDistance-minDistance, Robot.drivetrain.getGyro(), MAX_SPEED, MIN_SPEED));
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mode = Robot.drivetrain.swerveDrivetrain.getModeRobot();
    Robot.drivetrain.swerveDrivetrain.setModeRobot();
 
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(mode == CentricMode.FIELD){
      Robot.drivetrain.swerveDrivetrain.setModeField(); 
    } else {
      Robot.drivetrain.swerveDrivetrain.setModeRobot();
    }
    SmartDashboard.putNumber("horizontal speed", horizontalSpeed);
    SmartDashboard.putNumber("distance speed", -distanceSpeed);

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
