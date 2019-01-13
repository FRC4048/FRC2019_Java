package org.usfirst.frc4048.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4048.Robot;

public class DriveDistance extends Command {
    
    private double distance;
	private double distanceLeft = 0.0; //Distance left to travel
	private double lastDistance = 0.0;
	private double fwd, dir, rot;
	private boolean done = false;
	private boolean doTimeout = true;
	private final double MIN_SPEED = 0.3;
	private final double MAX_ERROR = 40;
	private final double TIMEOUT_DISTANCE = 15;
	private double time;
	/**
	 * Moves robot a certain distance
	 * @param distance - defines distance robot needs to travel
	 * @param fwd - sets forward/backward speed robot travels at
	 * @param dir - sets right/left speed robot travels at
	 * @param rot - sets clockwise/counter-clockwise speed robot travels at
	 */
	public DriveDistance(double distance, double fwd, double dir, double rot) {    	
		requires(Robot.drivetrain);
		this.distance = distance;
		this.fwd = fwd;
		this.dir = dir;
		this.rot = rot;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.setZero();
		lastDistance = Robot.drivetrain.getDistance();
		distanceLeft = distance;
		done = false;
		doTimeout = true;
		System.out.println("Travelling: " + this.distance + "in");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {


		if(doTimeout == false && Timer.getFPGATimestamp() - time > 2) {
			done = true;
		}
		else if(!done && Math.abs(Robot.drivetrain.getDistance() - lastDistance) < distanceLeft)
		{	
			if(distanceLeft <= TIMEOUT_DISTANCE && doTimeout == true) {
				time = Timer.getFPGATimestamp();
				doTimeout = false;
			}
			Robot.drivetrain.move(PIDCalc(fwd), PIDCalc(dir), rot);
			distanceLeft -= Math.abs(Robot.drivetrain.getDistance() - lastDistance);
			lastDistance = Robot.drivetrain.getDistance();
		}
		else
			done = true;
	}
	

	private double PIDCalc(double speed)
	{
		double scaledSpeed;

		if(speed == 0)
			scaledSpeed = 0;
		else
		{
			if(distanceLeft < MAX_ERROR) {
				scaledSpeed = (distanceLeft/MAX_ERROR)*(Math.abs(speed)-MIN_SPEED) + MIN_SPEED;
				if(speed < 0)
					scaledSpeed = -scaledSpeed;
			}
			else
				scaledSpeed = speed;
		}
		
		return scaledSpeed;
	}


	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}