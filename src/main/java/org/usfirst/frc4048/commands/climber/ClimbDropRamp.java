/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.climber;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class ClimbDropRamp extends LoggedCommand {
  private boolean done;
  public ClimbDropRamp() {
    super("ClimberDropRamp");
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    setTimeout(2);
    Robot.climber.movePiston(Value.kForward);
    done = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    //This is to free the piston once deployed
    if(Robot.climber.getPositionSensor()) {
      Robot.climber.movePiston(Value.kOff);
      done = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return done || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void loggedInterrupted() {
    end();
  }

  @Override
  protected void loggedCancel() {

  }
}
