/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.pneumatics;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;

public class ExampleSolenoidCommand extends LoggedCommand {
  private boolean state;
  /**
   * true is extend false is retract
   * @param state
   */
  public ExampleSolenoidCommand(boolean state) {
    super(String.format(" is running, state: %b", state));
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.state = state;

    requires(Robot.compressorSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    if(state == true) {
      Robot.solenoidSubsystem.extendPiston();
    } else {
      Robot.solenoidSubsystem.retractPiston();
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
