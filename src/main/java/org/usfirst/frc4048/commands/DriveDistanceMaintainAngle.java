/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistanceMaintainAngle extends Command {
  private double distance;
	private double distanceLeft = 0.0; //Distance left to travel
	private double lastDistance = 0.0;
  private double fwd, dir;
	private boolean done = false;
  private boolean doTimeout = true;
  private double startAngle = 0.0;
	private final double MIN_SPEED = 0.3;
	private final double MAX_ERROR = 40.0;
  private final double TIMEOUT_DISTANCE = 15.0;
  private final double MIN_ANGLE_ERROR = 2.0;
  private final double ROTATION_SPEED = 0.05;
	private double time;
	/**
	 * Moves robot a certain distance
	 * @param distance - defines distance robot needs to travel
	 * @param fwd - sets forward/backward speed robot travels at
	 * @param dir - sets right/left speed robot travels at
	 * 
	 */
	public DriveDistanceMaintainAngle(double distance, double fwd, double dir) {    	
		requires(Robot.drivetrain);
		this.distance = distance;
		this.fwd = fwd;
		this.dir = dir;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Robot.drivetrain.setZero();
		lastDistance = Robot.drivetrain.getDistance();
		distanceLeft = distance;
		done = false;
    doTimeout = true;
    time = 0;
    startAngle = Robot.drivetrain.getGyro();
    System.out.println("Travelling: " + this.distance + "in");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

    //if the timer is started and the timer is more than 2 seconds then we are travelling 15 inches
    //for more than 2 seconds and we stop the command
		if(time != 0 && Timer.getFPGATimestamp() - time > 2) {
			done = true;
		}
		if(!done && Math.abs(Robot.drivetrain.getDistance() - lastDistance) < distanceLeft)
		{
      //if the distance left is less than 15 and the timer has not started then it starts the timer.	
			if(distanceLeft <= TIMEOUT_DISTANCE && time == 0) {
				time = Timer.getFPGATimestamp();
      }
      
      double currAngle = Robot.drivetrain.getGyro();
      double rot = calcRot(startAngle, currAngle);
      
			Robot.drivetrain.move(PIDCalc(fwd), PIDCalc(dir), rot);
      SmartDashboard.putNumber("fwd", fwd);
      SmartDashboard.putNumber("dir", dir);
      distanceLeft -= Math.abs(Robot.drivetrain.getDistance() - lastDistance);
			lastDistance = Robot.drivetrain.getDistance();
		}
		else
			done = true;
	}
	
	private double calcRot(double startAngle, double currAngle) {
    if(Math.abs(startAngle-currAngle) < MIN_ANGLE_ERROR) {
      return 0;
    }
    //The Math.signum returns -1, 0, 1. So if current angle is larger than the starting angle 
    //then we need to turn counter clockwise. If the starting angle is larger than the current angle
    //we need to turn clockwise. The rotation value currently is hardcoded at 0.1.
    return Math.signum(startAngle-currAngle)*ROTATION_SPEED;
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
