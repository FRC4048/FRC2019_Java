package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.subsystems.DriveTrain;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.SmartShuffleboard;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PowerDistPanel extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static PowerDistributionPanel pdp;
	
	public PowerDistPanel() {
		pdp = new PowerDistributionPanel(RobotMap.PDP_CAN_ID);
	}

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
	public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.POWERDISTPANEL) {

		@Override
		protected void addAll() {
			add("Total Voltage", pdp.getVoltage());
			add("Total Current", pdp.getTotalCurrent());
			add("FR Steer", pdp.getCurrent(RobotMap.PDP_STEERING_FR));
			add("FL Steer", pdp.getCurrent(RobotMap.PDP_STEERING_FL));
			add("RL Steer", pdp.getCurrent(RobotMap.PDP_STEERING_RL));
			add("RR Steer", pdp.getCurrent(RobotMap.PDP_STEERING_RR));
			add("FR Drive", pdp.getCurrent(RobotMap.PDP_DRIVE_FR));
			add("FL Drive", pdp.getCurrent(RobotMap.PDP_DRIVE_FL));
			add("RL Drive", pdp.getCurrent(RobotMap.PDP_DRIVE_RL));
			add("RR Drive", pdp.getCurrent(RobotMap.PDP_DRIVE_RR));
			add("Limelight", pdp.getCurrent(RobotMap.PDP_LIMELIGHT));
			add("Cargo Intake", RobotMap.PDP_ID_CARGO_INTAKE);
			add("Elevator Motor", pdp.getCurrent(RobotMap.PDP_ELEVATOR_MOTOR));
		}
	};

    
    public void periodic() {
    	/*
    	 * Logging:
    	 *  Battery Voltage
    	 *  Swerve Drive Motors
    	 *  Swerve Steer Motors
    	 *  
    	 */
		loggingContext.writeData();
		Robot.completed(this, "pdp");
	}
	
    public PowerDistributionPanel getPDP() {
		return pdp;
	}
}
