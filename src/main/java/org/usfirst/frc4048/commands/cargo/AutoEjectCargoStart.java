package org.usfirst.frc4048.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;

public class AutoEjectCargoStart extends Command {
    public AutoEjectCargoStart() {
        requires(Robot.cargoSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        
    }
	

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.cargoSubsystem.leftLimitPressed() || Robot.cargoSubsystem.rightLimitPressed()) {
            Robot.cargoSubsystem.cargoOutput();
            setTimeout(0.2);
        }
        
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
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