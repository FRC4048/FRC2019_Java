/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.utils.LimeLightVision;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightDriveDistance extends Command {
  double startDistance;
  double minDistance;
  double currDistance;
  double distanceLeft;
  double speed;
  boolean done;

  /**
   * minDistance is the distance until the robot stops moving.
   * @param minDistance
   * @param speed
   */
  public LimelightDriveDistance(double minDistance, double speed) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.minDistance = minDistance;
    this.speed = speed;
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    distanceLeft = Robot.limelight.getDistance() - minDistance;
    // Robot.drivetrain.swerveDrivetrain.setModeRobot();
    done = false;
    setTimeout(10);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    distanceLeft = Robot.limelight.getDistance();// - minDistance;
    SmartDashboard.putNumber("distanceLeft", distanceLeft);
    
    if(!done && distanceLeft >= minDistance){
      //speed is inverted because camera is currently on back of robot.
      Robot.drivetrain.move(-speed, 0, 0);
    } else {
      done = true;
    }
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return done || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // Robot.drivetrain.swerveDrivetrain.setModeField();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
