/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.elevator;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.commands.extension.ExtensionMove;
import org.usfirst.frc4048.utils.ElevatorPosition;
import org.usfirst.frc4048.utils.WantedElevatorPosition;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class ElevatorMoveScheduler extends LoggedCommand {
  private WantedElevatorPosition wantedPos;

  public ElevatorMoveScheduler(WantedElevatorPosition wantedPos) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    super("ElevatorMoveScheduler");
    this.wantedPos = wantedPos;
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    if (Robot.gamePieceMode.isCargo()) {// true is cargo
      switch (wantedPos) {
      case ROCKET_HIGH:
        Scheduler.getInstance().add(new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_HIGH));
        break;
      case ROCKET_MID:
        Scheduler.getInstance().add(new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_MID));
        break;
      case ROCKET_LOW:
        Scheduler.getInstance().add(new ExtensionMove(false));
        Scheduler.getInstance().add(new ElevatorMoveToPos(ElevatorPosition.SAFE_HEIGHT));
        break;
      }
    } else {
      switch (wantedPos) {
      case ROCKET_HIGH:
        Scheduler.getInstance().add(new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_HIGH));
        break;
      case ROCKET_MID: 
        Scheduler.getInstance().add(new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_MID));
        break;
      case ROCKET_LOW: 
        Scheduler.getInstance().add(new ExtensionMove(false));
        Scheduler.getInstance().add(new ElevatorMoveToPos(ElevatorPosition.SAFE_HEIGHT));
        break;
      }
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
