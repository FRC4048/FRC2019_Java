/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.climber;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.utils.DoubleSolenoidUtil;

import edu.wpi.first.wpilibj.DriverStation;
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
    setTimeout(1);
    done = false;
    if(DriverStation.getInstance().getMatchTime() < 50){
      Robot.climber.movePiston(DoubleSolenoidUtil.State.forward);
    } else {
      done = true;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    //This is to free the piston once deployed
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return done || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
    Robot.climber.movePiston(DoubleSolenoidUtil.State.off);
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
