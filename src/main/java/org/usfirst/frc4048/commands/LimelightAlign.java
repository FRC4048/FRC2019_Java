/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.swerve.math.CentricMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightAlign extends CommandGroup {
  double distance;
  double horizontal;
  double horizontalSpeed;
  double distanceSpeed;
  double diagonalDistance;
  double angle = 0.0;
  final double minDistance = 20;
  final double ANGLE = 0.0;
  double speed = 0.4;
  CentricMode mode;

  public LimelightAlign() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
    distance = Robot.limelight.getDistance();
    horizontal = Robot.limelight.getHorizontal();
  
    diagonalDistance = Math.sqrt((horizontal*horizontal)+(distance*distance));
  
    horizontalSpeed = (horizontal/distance) * speed;
    horizontalSpeed *= -1; 
    distanceSpeed = speed;
    addSequential(new DriveDistanceMaintainAngle(diagonalDistance-minDistance, -distanceSpeed, horizontalSpeed));
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
