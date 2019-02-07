/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.elevator;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.utils.ElevatorPosition;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorMoveToPos extends LoggedCommand {
  private ElevatorPosition elevatorPosition;
  public ElevatorMoveToPos(ElevatorPosition elevatorPosition) {
    super("ElevatorMoveToPos: " + elevatorPosition.toString());
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.elevatorPosition = elevatorPosition;

    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    setTimeout(5);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    Robot.elevator.elevatorToPosition(elevatorPosition);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return isTimedOut() || Robot.elevator.elevatorAtPos(elevatorPosition) || !Robot.elevator.getTopSwitch() || !Robot.elevator.getBotSwitch();//we do this because on the test bed false is pushed down
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

  }
}