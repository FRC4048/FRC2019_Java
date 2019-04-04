/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.elevator;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.utils.ElevatorPosition;
import org.usfirst.frc4048.utils.SmartShuffleboard;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorMoveToPos extends LoggedCommand {
  private ElevatorPosition elevatorPosition;

  public ElevatorMoveToPos(ElevatorPosition elevatorPosition) {
    super("ElevatorMoveToPos: " + elevatorPosition.toString());
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.elevatorPosition = elevatorPosition;
    requires(Robot.elevator);
    requires(Robot.extension);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    if(Robot.elevator.getEncoder() > 800 && elevatorPosition == ElevatorPosition.SAFE_HEIGHT) {//about the safe height
      Robot.extension.setPiston(false);
    }
    setTimeout(2.5);
    
    Robot.elevator.setManualControl(false);
    
    Robot.elevator.elevatorToPosition(elevatorPosition);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return isTimedOut() || Robot.elevator.elevatorAtPos(elevatorPosition);
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
    if(elevatorPosition == ElevatorPosition.SAFE_HEIGHT){
      Robot.extension.setPiston(false);
    }
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