/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX elevatorMotor;

  public static enum Elevator_Position {
    HATCH_ROCKET_BOT, HATCH_ROCKET_MID, HATCH_ROCKET_HIGH, CARGO_ROCKET_LOW, CARGO_ROCKET_MID, CARGO_ROCKET_HIGH
  }

  private final int TIMEOUT = 100;
  
  private final double ELEVATOR_UP_SCALE_FACTOR = 1.00;
  private final double ELEVATOR_DOWN_SCALE_FACTOR = 0.50;

  private final double ELEVATOR_P = 0; 
  private final double ELEVATOR_I = 0;
  private final double ELEVATOR_D = 0;
  private final double ELEVATOR_F = 0;
  
  public Elevator() {
    elevatorMotor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_ID);
    elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    elevatorMotor.configNominalOutputForward(0, TIMEOUT);
    elevatorMotor.configNominalOutputReverse(0, TIMEOUT);
    elevatorMotor.configPeakOutputForward(ELEVATOR_UP_SCALE_FACTOR, TIMEOUT);
    elevatorMotor.configPeakOutputReverse(ELEVATOR_DOWN_SCALE_FACTOR, TIMEOUT);
    elevatorMotor.setNeutralMode(NeutralMode.Brake);

    elevatorMotor.selectProfileSlot(0, 0);
    elevatorMotor.config_kP(0, ELEVATOR_P, TIMEOUT);
    elevatorMotor.config_kI(0, ELEVATOR_I, TIMEOUT);
    elevatorMotor.config_kD(0, ELEVATOR_D, TIMEOUT);
    elevatorMotor.config_kF(0, ELEVATOR_F, TIMEOUT);
    elevatorMotor.configMotionAcceleration(0, TIMEOUT);
    elevatorMotor.configMotionCruiseVelocity(0, TIMEOUT);
    
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  
}
