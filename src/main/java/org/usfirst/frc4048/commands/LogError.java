package org.usfirst.frc4048.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.utils.Logging;

public class LogError extends LoggedCommand {

    public LogError() {
		super(String.format(" is running"));
    }

    // Called just before this Command runs the first time
	@Override
	protected void loggedInitialize() {
		Robot.logging.traceMessage(Logging.MessageLevel.INFORMATION, "~~~DriveTeam Breakpoint~~~");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void loggedExecute() {
       
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