
package org.usfirst.frc4048.commands.drive;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.swerve.math.CentricMode;
import org.usfirst.frc4048.utils.CameraAngles;
import org.usfirst.frc4048.utils.Logging;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class DriveAlignPhase3 extends LoggedCommand {
	private final double SIDEWAYS_POWER_FACTOR = 0.1; // factor to apply to forward power when adjusting horizontally
	private final double MIN_ANGLE_ERROR = 2.0; // The angle error that triggers correction
	private final double ROTATION_SPEED = 0.05; // The rotation correction power
	private final double DISTANCE_FROM_TARGET = 12; // required distance from target to drive to
	private double power; // power the robot will use to drive

	private double startAngle = 0.0; // Initial orientation of the robot (this is the one we will try to maintain), in degrees
	private boolean done = false;
	private CentricMode mode;

	// private NetworkTableEntry horizontalPowerEntry = Shuffleboard.getTab("Approach").add("horizontalPower", 0.0).getEntry();
	// private NetworkTableEntry distanceEntry = Shuffleboard.getTab("Approach").add("distance", 0.0).getEntry();


	/**
	 * Moves robot a certain distance while maintaining initial robot orientation
	 * 
	 * @param power  - total power to apply to drivetrain
	 * @param b
	 */
	public DriveAlignPhase3(double power, boolean isCamFront) {
		super(String.format(" is running, power=%f, isCamFront=%b", power, isCamFront));
		requires(Robot.drivetrain);

		this.power = power;
		if(!isCamFront){
			this.power *= -1;
		}
	}

	// Called just before this Command runs the first time
	protected void loggedInitialize() {
		// Robot.drivetrain.setZero();
		mode = Robot.drivetrain.swerveDrivetrain.getModeRobot();
		Robot.drivetrain.swerveDrivetrain.setModeRobot();
		startAngle = Robot.drivetrain.getGyro();
		if (Robot.gamePieceMode.isCargo()) {
			done = true;
		} else {
			done = false;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void loggedExecute() {
		CameraAngles cameraAngles = Robot.drivetrainSensors.getCameraAngles();
		if(cameraAngles == null){
			System.out.println("------------NO CAMERA VALUE------------");
			Robot.logging.traceMessage(Logging.MessageLevel.INFORMATION, "------------NO CAMERA VALUE------------");
			done = true;
			return;
		} else {
			Robot.logging.traceMessage(Logging.MessageLevel.INFORMATION, "------------VAILD CAMERA VALUE------------");
		}
		double horizontalAngleToTarget = cameraAngles.getTx();
		double forwardDistance = Robot.drivetrainSensors.getUltrasonicDistance();

		if (forwardDistance < DISTANCE_FROM_TARGET) {
			done = true;
			return;
		}

		double horizontalPower = horizontalAngleToTarget * SIDEWAYS_POWER_FACTOR * power;
		double currAngle = Robot.drivetrain.getGyro();
		double rot = calcRot(startAngle, currAngle);

		Robot.drivetrain.move(power, horizontalPower, rot);

		// horizontalPowerEntry.setDouble(horizontalPower);
		// distanceEntry.setDouble(forwardDistance);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean loggedIsFinished() {
		return done;
	}

	// Called once after isFinished returns true
	protected void loggedEnd() {
		if(mode.equals(CentricMode.FIELD)) {
			Robot.drivetrain.swerveDrivetrain.setModeField();
		} else if(mode.equals(CentricMode.ROBOT_SLOW)) {
			Robot.drivetrain.swerveDrivetrain.setModeRobotSlow();
		} else {
			Robot.drivetrain.swerveDrivetrain.setModeRobot();
		}
		Robot.drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void loggedInterrupted() {
		loggedEnd();
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

	@Override
	protected void loggedCancel() {
		loggedEnd();
	}
}