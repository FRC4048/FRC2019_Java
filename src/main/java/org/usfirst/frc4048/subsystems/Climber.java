/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.utils.AngleFinder;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.OpticalRangeFinder;
import org.usfirst.frc4048.utils.SmartShuffleboard;

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
  private CANSparkMax winch;
  private Solenoid climberPiston;
  private AnalogInput leftDistanceSensorAnalogInput;
  private AnalogInput rightDistanceSensorAnalogInput;
  private OpticalRangeFinder leftRangeFinder;
  private OpticalRangeFinder rightRangeFinder;
  private AngleFinder angleFinder;

  private final double RANGE_FINDER_DISTANCE_APART = 20;
  public Climber() {
    winch = new CANSparkMax(RobotMap.WINCH_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    climberPiston = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.CLIMBER_PISTONS_ID);
    angleFinder = initClimberAngleFinder();
    winch.setIdleMode(CANSparkMax.IdleMode.kBrake);
  }

  public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(this.getClass()) {

		protected void addAll() {
      add("Angle", getAngle());
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
      // PUT SHUFFLEBOARD CODE HERE
      Robot.completed(this, "shuf");
    }
  }

  public double getAngle() {
    return angleFinder.calcAngleInDegrees();
  }

  public void controlWinch(double speed) {
    winch.set(speed);
  } 

  public void movePiston(boolean state) {
    climberPiston.set(state);
  }

  /**
   * Initialize the climber angle finder. Standalone function since it involves
   * multiple steps.
   */
  private static AngleFinder initClimberAngleFinder() {
    final AnalogInput leftRangeInput = new AnalogInput(RobotMap.CLIMBER_DISTANCE_SENSOR_LEFT_ID);
    final AnalogInput rightRangeInput = new AnalogInput(RobotMap.CLIMBER_DISTANCE_SENSOR_RIGHT_ID);
    final AnalogInput all[] = { leftRangeInput, rightRangeInput };
    for (AnalogInput x : all) {
      // These AnalogInput settings provided stable and fast results.
      x.setOversampleBits(RobotMap.CLIMBER_DISTANCE_SENSOR_OVERSAMPLE_BITS);
      x.setAverageBits(RobotMap.CLIMBER_DISTANCE_SENSOR_AVERAGE_BITS);
    }

    final OpticalRangeFinder leftRangeFinder = new OpticalRangeFinder(leftRangeInput);
    final OpticalRangeFinder rightRangeFinder = new OpticalRangeFinder(rightRangeInput);
    return new AngleFinder(leftRangeFinder, rightRangeFinder, RobotMap.INCHES_BETWEEN_CLIMBER_DISTANCE_SENSORS);
  }
}
