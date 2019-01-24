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

public class ScheduleLimelightAlign extends Command {
  private double angle; 
  private final double ANGLE_MARGIN_VALUE = 2;
  private boolean done;
  public ScheduleLimelightAlign() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    angle = 0;
    done = false;
    Scheduler.getInstance().add(new RotateAngle(angle));
    setTimeout(2);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // System.out.println("SCHEDULER ALIGNMENT COMMAND IS RUNNING");
    // Scheduler.getInstance().add(new LimelightOn());
    if(Math.abs(Robot.drivetrain.getGyro() - angle) <= ANGLE_MARGIN_VALUE){
      Scheduler.getInstance().add(new LimelightAlignToTargetGroup());
      done = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(isTimedOut()) {
      System.out.println("Timedout!!!!!!!!");
    }
    return done || isTimedOut();
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
