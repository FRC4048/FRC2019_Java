package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.swerve.math.CentricMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistanceMaintainAngle extends Command {
	private final double MAX_ERROR = 40.0; // Distance, in Inches, for the PID portion of the path
	private final double TIMEOUT_DISTANCE = 15.0; // Distance, in Inches, for the portion of the path where timeout is
																								// calculated
	private final double MIN_ANGLE_ERROR = 2.0; // The angle error that triggers correction
	private final double ROTATION_SPEED = 0.05; // The rotation correction power

	private double totalDistance; // Total distance to travel
	private double headingAngle; // Angle of travel, measured from the robot heading, in Radians
	private double pMax; // Maximum power the drivetrain will get
	private double pMin; // Minimum power drivetrain will get

	private double initialDrivetrainDistance; // Initial value for the robot distance from the encoder
	private boolean done = false;
	private double startAngle = 0.0; // Initial orientation of the robot (this is the one we will try to maintain), in degrees
	private double time = 0; // time since starting the PID section of the route, in seconds
	private CentricMode mode;
	/**
	 * Moves robot a certain distance while maintaining initial robot orientation
	 * 
	 * @param totalDistance - defines distance robot needs to travel
	 * @param headingAngle  - angle the robot travels (measured from the robot's heading) in degrees
	 * @param pMax          - the maximum power given to the drivetrain (between 0-1)
	 * @param pMin          - the minimum power to use (such that the command will not go below this setting) (between 0-1)
	 */
	public DriveDistanceMaintainAngle(double totalDistance, double headingAngle, double pMax, double pMin) {
		requires(Robot.drivetrain);

		this.totalDistance = totalDistance;
		this.headingAngle = Math.toRadians(headingAngle);
		this.pMax = pMax;
		this.pMin = pMin;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Robot.drivetrain.setZero();
		mode = Robot.drivetrain.swerveDrivetrain.getModeRobot();
		Robot.drivetrain.swerveDrivetrain.setModeRobot();
		
		initialDrivetrainDistance = Robot.drivetrain.getDistance();
		startAngle = Robot.drivetrain.getGyro();
		done = false;
		time = 0;
		System.out.println("Travelling: " + this.totalDistance);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// if the timer is started and the timer is more than 2 seconds then we are
		// travelling 15 inches
		// for more than 2 seconds and we stop the command
		// TODO: use command timeout
		if (time != 0 && Timer.getFPGATimestamp() - time > 2.0) {
			done = true;
			return;
		}

		double traveledDistance = Math.abs(Robot.drivetrain.getDistance() - initialDrivetrainDistance);
		double distanceLeft = totalDistance - traveledDistance;
		SmartDashboard.putNumber("Distance Left", distanceLeft);
		if (distanceLeft <= 0) {
			done = true;
			return;
		}

		// if the distance left is less than 15 and the timer has not started then it
		// starts the timer.
		if ((time == 0) && (distanceLeft <= TIMEOUT_DISTANCE)) {
			time = Timer.getFPGATimestamp();
		}

		double power = pidCalc(distanceLeft, pMax, pMin);
		double pFwd = power * Math.cos(headingAngle);
		double pDir = power * Math.sin(headingAngle);
		SmartDashboard.putNumber("power", power);
		SmartDashboard.putNumber("pFwd", pFwd);
		SmartDashboard.putNumber("pDir", pDir);
		double currAngle = Robot.drivetrain.getGyro();
		double rot = calcRot(startAngle, currAngle);

		Robot.drivetrain.move(pFwd, pDir, rot);
		SmartDashboard.putNumber("power", power);
		SmartDashboard.putNumber("fwd", pFwd);
		SmartDashboard.putNumber("dir", pDir);
		SmartDashboard.putNumber("rot", rot);
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

	/**
	 * Calculate the power to drive based off of the remaining distance
	 * 
	 * @param distanceLeft distance (in Inch) left to travel
	 * @param pMax         the maximum power to drive at
	 * @param pMin         the minimum power to drive at
	 * @return the power to drive at (between 0-1)
	 */
	private double pidCalc(double distanceLeft, double pMax, double pMin) {
		if (distanceLeft > MAX_ERROR) {
			return pMax;
		}
		double pidRatio = distanceLeft / MAX_ERROR;
		SmartDashboard.putNumber("pidRatio", pidRatio);
		if (pidRatio < Math.abs(pMin)) {
			return pMin;
		} else {
			return pidRatio * pMax;
		}
		
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
}