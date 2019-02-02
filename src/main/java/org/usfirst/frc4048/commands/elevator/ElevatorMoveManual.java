/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.elevator;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorMoveManual extends Command {
  private final double TRIGGER_MARGIN_VALUE = 0.01;
  private int finetuneRate;

  public ElevatorMoveManual() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    finetuneRate = 60;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.oi.getRightTrigger() > 0.0 + TRIGGER_MARGIN_VALUE) {
      finetuneRate *= Robot.oi.getRightTrigger();
      Robot.elevator.fineTune(finetuneRate);
    } else if(Robot.oi.getLeftTrigger() > 0.0 + TRIGGER_MARGIN_VALUE) {
      finetuneRate *= Robot.oi.getLeftTrigger();
      Robot.elevator.fineTune(-finetuneRate);
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
    end();
  }
}
