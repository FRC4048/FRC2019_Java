package org.usfirst.frc4048.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.utils.MotorUtils;

public class IntakeCargo extends LoggedCommand {

	//NOTE: The timeout for the cargo intake motor is currently set to half a second. This should be confirmed/updated with further testing.
	private MotorUtils util = new MotorUtils(RobotMap.PDP_ID_CARGO_INTAKE, RobotMap.CURRENT_THRESHOLD_CARGO_INTAKE, 0.5);

	public IntakeCargo() {
		super(" is running");
		requires(Robot.cargoSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void loggedInitialize() {
		Robot.cargoSubsystem.cargoInput();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void loggedExecute() {
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean loggedIsFinished() {
		return Robot.cargoSubsystem.cargoInIntake() || util.isStalled();
	}

	// Called once after isFinished returns true
	@Override
	protected void loggedEnd() {
		Robot.cargoSubsystem.cargoStop();
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