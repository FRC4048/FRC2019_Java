package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.swerve.math.CentricMode;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class DriveTargetCenter extends Command {
	private final double SIDEWAYS_POWER_FACTOR = 0.1; // factor to apply to forward power when adjusting horizontally
	private final double MIN_ANGLE_ERROR = 2.0; // The angle error that triggers correction
	private final double ROTATION_SPEED = 0.05; // The rotation correction power

	private double distanceFromTarget; // required distance from target to drive to
	private double power; // power the robot will use to drive

	private double startAngle = 0.0; // Initial orientation of the robot (this is the one we will try to maintain), in degrees
	private boolean done = false;
	private CentricMode mode;

	private NetworkTableEntry horizontalPowerEntry = Shuffleboard.getTab("Approach").add("horizontalPower", 0.0).getEntry();
	private NetworkTableEntry distanceEntry = Shuffleboard.getTab("Approach").add("distance", 0.0).getEntry();


	/**
	 * Moves robot a certain distance while maintaining initial robot orientation
	 * 
	 * @param distanceFromTarget - Distance from target for the robot to drive to
	 * @param distanceFromTarget  - total power to apply to drivetrain
	 */
	public DriveTargetCenter(double distanceFromTarget, double power) {
		requires(Robot.drivetrain);

		this.distanceFromTarget = distanceFromTarget;
		this.power = power;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Robot.drivetrain.setZero();
		mode = Robot.drivetrain.swerveDrivetrain.getModeRobot();
		Robot.drivetrain.swerveDrivetrain.setModeRobot();
		startAngle = Robot.drivetrain.getGyro();

		done = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double horizontalAngleToTarget = Robot.drivetrainSensors.getHorizontalAngle();
		double forwardDistance = Robot.drivetrainSensors.getUltrasonicDistance();

		if (forwardDistance < distanceFromTarget) {
			done = true;
			return;
		}

		double horizontalPower = horizontalAngleToTarget * SIDEWAYS_POWER_FACTOR * power;
		double currAngle = Robot.drivetrain.getGyro();
		double rot = calcRot(startAngle, currAngle);

		Robot.drivetrain.move(power, horizontalPower, rot);

		horizontalPowerEntry.setDouble(horizontalPower);
		distanceEntry.setDouble(forwardDistance);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
		if(mode.equals(CentricMode.FIELD)) {
			Robot.drivetrain.swerveDrivetrain.setModeField();
		} else {
			Robot.drivetrain.swerveDrivetrain.setModeRobot();
		}
		Robot.drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	private double calcRot(double startAngle, double currAngle) {
		if (Math.abs(startAngle - currAngle) < MIN_ANGLE_ERROR) {
			return 0;
		}
		// The Math.signum returns -1, 0, 1. So if current angle is larger than the
		// starting angle
		// then we need to turn counter clockwise. If the starting angle is larger than
		// the current angle
		// we need to turn clockwise. The rotation value currently is hardcoded at 0.1.
		return Math.signum(startAngle - currAngle) * ROTATION_SPEED;
	}
}