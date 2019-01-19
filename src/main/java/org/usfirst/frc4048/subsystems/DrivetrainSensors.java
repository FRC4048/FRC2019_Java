/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * This subsystem holds the sensors the robot uses for navigation
 */
public class DrivetrainSensors extends Subsystem {

  private Ultrasonic ultrasonic;

  private NetworkTableEntry unltrasonicEntry = Shuffleboard.getTab("Approach").add("Ultrasonic", 0.0).getEntry();

  public DrivetrainSensors() {
    ultrasonic = new Ultrasonic(8, 9);
    ultrasonic.setAutomaticMode(true);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void periodic() {
    // Put code here to be run every loop
    unltrasonicEntry.setDouble(ultrasonic.getRangeInches());

  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void init() {
  }

  public double getUltrasonicDistance() {
    return ultrasonic.getRangeInches();
  }

}
