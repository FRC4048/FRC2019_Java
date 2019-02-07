package org.usfirst.frc4048.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

public class AutoEjectCargoEnd extends LoggedCommand {
  public AutoEjectCargoEnd() {
    super(" is running");
    requires(Robot.cargoSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void loggedInitialize() {
    setTimeout(5.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
	protected void loggedExecute() {
    //Checks to see if any limit switches are pressed. If so, then the piston will extend, ejecting the ball. It will then set the timeout
    //to one second, in order to make sure the ball is fully out of the intake before ending the command. It also checks to make sure
    //the piston has not already been extended, so that the timeout does not get reset.
    if ((Robot.cargoSubsystem.leftLimitPressed() || Robot.cargoSubsystem.rightLimitPressed()) && !Robot.cargoSubsystem.getCargoPiston()) {
      Robot.cargoSubsystem.cargoEject();
      setTimeout(1.0);
    }
    
	}

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean loggedIsFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void loggedEnd() {
    Robot.cargoSubsystem.cargoStop();
    Robot.cargoSubsystem.cargoRetract();
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