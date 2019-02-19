/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.pivot;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.Command;

public class MovePivot extends Command {
  private final double PIVOT_SPEED = 0.;
  public MovePivot() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.pivot);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.pivot.pivotDeployed){
      if (Robot.pivot.getDeployedSwitch()){
        Robot.pivot.setSpeed(0);
      }
      else {
        Robot.pivot.setSpeed(PIVOT_SPEED);
      }

    } else {
      if (Robot.pivot.getRetractedSwitch()){
        Robot.pivot.setSpeed(0);

      } else {
        Robot.pivot.setSpeed(-PIVOT_SPEED);
      }

    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
