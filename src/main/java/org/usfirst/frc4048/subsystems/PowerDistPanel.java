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
	public long last_periodic = -1;

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
			add("Cargo Intake", RobotMap.PDP_ID_CARGO_INTAKE);
		}
	};

    
    public void periodic() {
		final long start = System.currentTimeMillis();
    	/*
    	 * Logging:
    	 *  Battery Voltage
    	 *  Swerve Drive Motors
    	 *  Swerve Steer Motors
    	 *  
    	 */
		loggingContext.writeData();
		last_periodic = System.currentTimeMillis() - start;
	}
	
    public PowerDistributionPanel getPDP() {
		return pdp;
	}
}
