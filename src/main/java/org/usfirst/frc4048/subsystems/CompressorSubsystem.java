/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.RobotMap;

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
  private Solenoid exampleSolenoid;

  public CompressorSubsystem() {
    compressor = new Compressor(RobotMap.PCM_CAN_ID);
    exampleSolenoid = new Solenoid(RobotMap.PCM_CAN_ID, 0);
    compressor.setClosedLoopControl(true);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
 
  /**
   * This returns true if the pressure is too low
   * @return
   */
  public boolean getPressure() {
    return compressor.getPressureSwitchValue();
  }

  public double getCurrent() {
    return compressor.getCompressorCurrent();
  }
  public void extendPiston() {
    exampleSolenoid.set(true);
  }

  public void retractPiston() {
    exampleSolenoid.set(false);
  } 
}
