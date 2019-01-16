package org.usfirst.frc4048.utils;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.subsystems.DriveTrain;
import org.usfirst.frc4048.utils.Logging;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PowerDistPanel extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static PowerDistributionPanel pdp = RobotMap.pdp;
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void init() {
    	  pdp = new PowerDistributionPanel(0);
    }
    
	public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.POWERDISTPANEL) {

		@Override
		protected void addAll() {
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
    }
    
}
