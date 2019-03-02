/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.PressureSensor;
import org.usfirst.frc4048.utils.SmartShuffleboard;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class CompressorSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Compressor compressor;
  private PressureSensor pressureSensor;

  public CompressorSubsystem() {
    compressor = new Compressor(RobotMap.PCM_CAN_ID);
    pressureSensor = new PressureSensor(new AnalogInput(RobotMap.COMPRESSOR_PRESSURE_SENSOR));
    compressor.setClosedLoopControl(true);
  }

  public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(this.getClass()) {

		protected void addAll() {
      add("Pressure", getPressure());
      add("Pressure Switch", getPressureSwitch());
      add("Current", getCurrent());
		}
  };

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
 
  @Override
  public void periodic() {

    // Put code here to be run every loop
    if (RobotMap.SHUFFLEBOARD_DEBUG_MODE) {
      SmartShuffleboard.put("Compressor", "Current", getCurrent());
      SmartShuffleboard.put("Compressor", "Pressure", getPressure());    
      Robot.completed(this, "shuf");
    }
  }

  /**
   * This returns true if the pressure is too low
   * @return
   */
  public boolean getPressureSwitch() {
    return compressor.getPressureSwitchValue();
  }

  public double getPressure() {
    return pressureSensor.getPressureInPSI();
  }

  public double getCurrent() {
    return compressor.getCompressorCurrent();
  }
}
