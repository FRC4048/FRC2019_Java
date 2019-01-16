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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightAlign extends Command {
  double distance;
  double horizontal;
  double horizontalSpeed;
  double distanceSpeed;
  double diagonalDistance;
  double angle = 0.0;
  final double minDistance = 20;
  final double ANGLE = 0.0;
  double speed = 0.4;


  public LimelightAlign() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.drivetrain.swerveDrivetrain.setModeRobot();
    distance = Robot.limelight.getDistance();
    horizontal = Robot.limelight.getHorizontal();
  
    SmartDashboard.putNumber("angle", angle);
    diagonalDistance = Math.sqrt((horizontal*horizontal)+(distance*distance));
    
    SmartDashboard.putNumber("diagonal distance", diagonalDistance);
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    horizontalSpeed = (horizontal/distance) * speed;
    horizontalSpeed *= -1; 
    distanceSpeed = speed;
    Scheduler.getInstance().add(new DriveDistanceMaintainAngle(diagonalDistance-minDistance, -distanceSpeed, horizontalSpeed));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivetrain.swerveDrivetrain.setModeField(); //TODO Change mode not set to field
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
