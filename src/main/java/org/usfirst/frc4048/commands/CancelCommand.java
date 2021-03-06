/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.commands.elevator.ElevatorMoveToPos;
import org.usfirst.frc4048.commands.manipulator.hatchpanel.HatchPanelRelease;
import org.usfirst.frc4048.utils.ElevatorPosition;
import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

public class CancelCommand extends LoggedCommand {
  public CancelCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    super("Cancel Command");
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    Scheduler.getInstance().removeAll();
    // Scheduler.getInstance().add(new HatchPanelRelease());
    Robot.elevator.stopMotor();
    Robot.elevator.setManualControl(true);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
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
  }

  @Override
  protected void loggedCancel() {
    
  }
}
