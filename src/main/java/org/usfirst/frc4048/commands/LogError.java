package org.usfirst.frc4048.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.utils.Logging;

public class LogError extends Command {

  

    public LogError() {
      
    }

    // Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.logging.traceMessage(Logging.MessageLevel.INFORMATION, "~~~DriveTeam Breakpoint~~~");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
       
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
        end();
	}

}