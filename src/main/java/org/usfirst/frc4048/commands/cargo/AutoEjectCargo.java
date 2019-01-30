package org.usfirst.frc4048.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;

public class AutoEjectCargo extends Command {
    public AutoEjectCargo() {
        requires(Robot.cargoSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.cargoSubsystem.cargoOutput();
    }
	

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //TODO Make it eject only when motor is fully sped up.
        if (Robot.cargoSubsystem.leftLimitPressed() || Robot.cargoSubsystem.rightLimitPressed())
            Robot.cargoSubsystem.cargoEject();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !Robot.cargoSubsystem.cargoInIntake();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.cargoSubsystem.cargoStop();
        Robot.cargoSubsystem.cargoRetract();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}