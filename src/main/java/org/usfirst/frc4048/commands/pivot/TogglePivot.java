       /*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.pivot;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class TogglePivot extends Command {
  public final double MAX_ELEVATOR_HEIGHT_FOR_TOGGLE = 600;
  private boolean done = false;

  public TogglePivot() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (RobotMap.ENABLE_ELEVATOR){
      if (Robot.elevator.getEncoder() > MAX_ELEVATOR_HEIGHT_FOR_TOGGLE) { 
         done = true;
      }
    }

    if (!done) { 
      Robot.pivot.toggleState();
    }
  }


  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Scheduler.getInstance().add(new MovePivot());//calls the actual movement of the pivot after toggling.
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
