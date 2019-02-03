/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.utils.OpticalRangeFinder;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Spark winch;
  private Solenoid climberPiston;
  private OpticalRangeFinder leftDistanceSensor;
  private OpticalRangeFinder rightDistanceSensor;

  public Climber() {
    winch = new Spark(RobotMap.WINCH_MOTOR_ID);
    climberPiston = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.CLIMBER_PISTONS_ID);
    leftDistanceSensor = new OpticalRangeFinder(new AnalogInput(RobotMap.CLIMBER_DISTANCE_SENSOR_LEFT_ID));
    rightDistanceSensor = new OpticalRangeFinder(new AnalogInput(RobotMap.CLIMBER_DISTANCE_SENSOR_RIGHT_ID));
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getLeftSensorDistance() {
    return leftDistanceSensor.getDistanceInInches();
  }

  public double getRightSensorDistance() {
    return rightDistanceSensor.getDistanceInInches();
  }

  public void controlWinch(double speed) {
    winch.set(speed);
  } 

  public void movePiston(boolean state) {
    climberPiston.set(state);
  }
}
