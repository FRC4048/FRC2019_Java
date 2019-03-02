package org.usfirst.frc4048.commands.drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.subsystems.DriveTrain;
import org.usfirst.frc4048.swerve.math.CentricMode;
import org.usfirst.frc4048.utils.SmartShuffleboard;

public class Drive extends Command {

	private double fwd, str, rcw;
	private boolean scaleSpeed;
	private CentricMode mode;

	public Drive() {
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		scaleSpeed = false;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		fwd = -Robot.oi.getLeftJoy().getY();
		str = Robot.oi.getLeftJoy().getX();
		rcw = Robot.oi.getRightJoy().getX();
		Robot.completed(this, "getJoy");

		mode = Robot.drivetrain.swerveDrivetrain.getModeRobot();
		if (mode == CentricMode.ROBOT_SLOW) {
			scaleSpeed = true;
		} else {
			scaleSpeed = false;
		}

		if (!scaleSpeed) {
			// Cube the values for finer movement
			fwd = Math.pow(fwd, 3);
			str = Math.pow(fwd, 3);
			rcw = Math.pow(rcw, 3);
		}

		if (scaleSpeed) {
			fwd *= RobotMap.ROBOT_CENTRIC_SCALE_RATIO;
			str *= RobotMap.ROBOT_CENTRIC_SCALE_RATIO;
			rcw *= RobotMap.ROBOT_CENTRIC_SCALE_RATIO;
		}

		Robot.completed(this, "getMode");
		if (RobotMap.SHUFFLEBOARD_DEBUG_MODE) {
			SmartShuffleboard.put("DrivetrainSensors", "drive-cmd-rotation", rcw);
			Robot.completed(this, "shuf");
		}

		Robot.drivetrain.move(fwd, str, rcw);
		Robot.completed(this, "move");
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
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