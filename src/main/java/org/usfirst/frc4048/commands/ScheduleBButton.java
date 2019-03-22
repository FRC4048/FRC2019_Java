/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveToPos;
import org.usfirst.frc4048.commands.limelight.LimelightBlink;
import org.usfirst.frc4048.utils.ElevatorPosition;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class ScheduleBButton extends LoggedCommand {
  public ScheduleBButton() {
    super("Schedule Blink/MoveElevator");
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    if(Robot.gamePieceMode.isCargo()) {
      Scheduler.getInstance().add(new ElevatorMoveToPos(ElevatorPosition.CARGO_CARGOSHIP_POS));
    } else {
      Scheduler.getInstance().add(new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_BOT));
    }
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
    loggedEnd();
  }

  @Override
  protected void loggedCancel() {
    loggedEnd();
  }
}
