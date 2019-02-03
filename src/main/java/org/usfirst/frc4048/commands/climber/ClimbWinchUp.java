/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.climber;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbWinchUp extends LoggedCommand {
  private final double WINCH_SPEED = 1.0;
  private final double PITCH_MARGIN_VALUE = 2; //in degrees
  private boolean done;
  
  public ClimbWinchUp() {
    super("ClimbWinchUp");
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    done = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void loggedExecute() {
    double pitch = Robot.drivetrain.getPitch();
    
    if(Math.abs(pitch) > 0 + PITCH_MARGIN_VALUE) {
      done = true;
    } else {
      Robot.climber.controlWinch(-WINCH_SPEED);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return done;
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
    Robot.climber.controlWinch(0.0);
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
