/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.manipulator;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.commands.manipulator.cargo.CargoEjectGroup;
import org.usfirst.frc4048.commands.manipulator.cargo.IntakeCargo;
import org.usfirst.frc4048.commands.manipulator.hatchpanel.HatchPanelIntake;
import org.usfirst.frc4048.commands.manipulator.hatchpanel.HatchPanelRelease;
import org.usfirst.frc4048.utils.SmartShuffleboard;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class ReleaseGamePieceScheduler extends LoggedCommand {
  public ReleaseGamePieceScheduler() {
    super("ReleaseGamePieceScheduler");
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
    if(Robot.gamePieceMode.isCargo()){ //true is cargo
      Scheduler.getInstance().add(new CargoEjectGroup());//this will change with new mechanism
    } else {
      Scheduler.getInstance().add(new HatchPanelRelease());
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
