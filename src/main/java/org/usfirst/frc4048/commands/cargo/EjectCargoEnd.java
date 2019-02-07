package org.usfirst.frc4048.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

public class EjectCargoEnd extends LoggedCommand {
    public EjectCargoEnd() {
        super(" is running");
        requires(Robot.cargoSubsystem);
    }

    // Called just before this Command runs the first time
	@Override
	protected void loggedInitialize() {
		setTimeout(1.0);
        Robot.cargoSubsystem.cargoEject();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void loggedExecute() {
   
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