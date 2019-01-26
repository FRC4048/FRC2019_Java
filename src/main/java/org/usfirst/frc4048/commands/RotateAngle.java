package org.usfirst.frc4048.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4048.Robot;

public class RotateAngle extends Command {

    public double angle;
    private final double ANGLE_TOLERANCE = 3; // Defines angle tolernace used when going to a specific location
    private final double MAX_SPEED = 0.3;
    private final double MIN_SPEED = 0.25;

    public RotateAngle(double angle) {
        requires(Robot.drivetrain);

        this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double error;
        double speed = 0.0;
        final double currAngle = Robot.drivetrain.getGyro();

        if (Math.abs(angle - currAngle) < ANGLE_TOLERANCE) {
            speed = 0.0;
        } else {
            if (Math.abs(currAngle - angle) > 180) {
                if (currAngle > angle)
                    angle += 360;
                else
                    angle -= 360;
            }
            // 180 is the maximum error
            error = angle - currAngle;

            speed = (error / 180) * (MAX_SPEED - MIN_SPEED);
            if (error < 0)
                speed -= MIN_SPEED;
            else
                speed += MIN_SPEED;

            if (Math.abs(angle - currAngle) < ANGLE_TOLERANCE)
                speed = 0;
        }
        Robot.drivetrain.move(0.0, 0.0, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(angle - Robot.drivetrain.getGyro()) < ANGLE_TOLERANCE;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.drivetrain.stop();
    }

}