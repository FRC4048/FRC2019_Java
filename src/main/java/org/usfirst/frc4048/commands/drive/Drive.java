package org.usfirst.frc4048.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.swerve.math.CentricMode;

public class Drive extends Command {

    double fwd, str, rcw;
    public Drive() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
	@Override
	protected void initialize() {
        
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        fwd = -Robot.oi.getLeftJoy().getY();
        str = Robot.oi.getLeftJoy().getX();
		rcw = Robot.oi.getRightJoy().getX();
		Robot.completed(this, "getJoy");
        
        //Square the values for finer movement
    	if(fwd < 0)
    		fwd *= fwd * -1;
    	else
    		fwd *= fwd;
    	
    	if(str < 0)
    		str *= str * -1;
    	else
    		str *= str;
    	
    	if(rcw < 0)
    		rcw *= rcw * -1;
    	else
    		rcw *= rcw;
		
		if(Robot.drivetrain.swerveDrivetrain.getModeRobot() == CentricMode.ROBOT) {
			fwd *= RobotMap.ROBOT_CENTRIC_SCALE_RATIO;
			str *= RobotMap.ROBOT_CENTRIC_SCALE_RATIO;
			rcw *= RobotMap.ROBOT_CENTRIC_SCALE_RATIO;
		}
		Robot.completed(this, "getMode");
		
		Robot.drivetrain.move(fwd, str, rcw);
		Robot.completed(this,"move");
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