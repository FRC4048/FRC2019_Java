package org.usfirst.frc4048.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

public class AutoEjectCargoStart extends LoggedCommand {
    public AutoEjectCargoStart() {
        super(" is running");
        requires(Robot.cargoSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void loggedInitialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void loggedExecute() {
        if (Robot.cargoSubsystem.leftLimitPressed() || Robot.cargoSubsystem.rightLimitPressed()) {
            Robot.cargoSubsystem.cargoOutput();
            setTimeout(0.2);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void loggedInterrupted() {
        Robot.cargoSubsystem.cargoStop();
        loggedEnd();
    }

    @Override
    protected void loggedCancel() {
        loggedEnd();
    }
}