/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveManual;
import org.usfirst.frc4048.utils.ElevatorPosition;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX elevatorMotor;

  private final int TIMEOUT = 100;

  private final double ELEVATOR_POSITION_ERROR = 0;

  private final double ELEVATOR_UP_SCALE_FACTOR = 1.00;
  private final double ELEVATOR_DOWN_SCALE_FACTOR = 0.50;

  private final double ELEVATOR_P = 0; 
  private final double ELEVATOR_I = 0;
  private final double ELEVATOR_D = 0;
  private final double ELEVATOR_F = 0;
  private final int ELEVATOR_ACCEL = 281; //initial acceleration is 75% of the RPM, this may change 
  private final int ELEVATOR_CRUISE_VELOCITY = 281; // ^

  public Elevator() {
    elevatorMotor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_ID);
    elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    elevatorMotor.configNominalOutputForward(0, TIMEOUT);
    elevatorMotor.configNominalOutputReverse(0, TIMEOUT);
    elevatorMotor.configPeakOutputForward(ELEVATOR_UP_SCALE_FACTOR, TIMEOUT);
    elevatorMotor.configPeakOutputReverse(ELEVATOR_DOWN_SCALE_FACTOR, TIMEOUT);
    elevatorMotor.setNeutralMode(NeutralMode.Brake);

    
    elevatorMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
    elevatorMotor.selectProfileSlot(0, 0);
    elevatorMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
    elevatorMotor.config_kP(0, ELEVATOR_P, TIMEOUT);
    elevatorMotor.config_kI(0, ELEVATOR_I, TIMEOUT);
    elevatorMotor.config_kD(0, ELEVATOR_D, TIMEOUT);
    elevatorMotor.config_kF(0, ELEVATOR_F, TIMEOUT);
    elevatorMotor.configMotionAcceleration(ELEVATOR_ACCEL, TIMEOUT);
    elevatorMotor.configMotionCruiseVelocity(ELEVATOR_CRUISE_VELOCITY, TIMEOUT);
    elevatorMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevatorMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    
    resetEncoder();
  }

  @Override
  public void periodic() {
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ElevatorMoveManual());
  }

  public void setPosition(double position) {
    elevatorMotor.set(ControlMode.MotionMagic, position, DemandType.ArbitraryFeedForward, 0.05); //may change this number is the amount of speed sent to the motor to keep it in place
  }

  public boolean elevatorAtPos(ElevatorPosition elevatorPosition) {
    double position = elevatorPosition.getPosition();
    return Math.abs(getError()) < ELEVATOR_POSITION_ERROR && Math.abs(getTrajectoryPos() - position) < ELEVATOR_POSITION_ERROR;
  }
  

  public int getTrajectoryPos() {
    return elevatorMotor.getActiveTrajectoryPosition();
  }

  public int getError() {
    return elevatorMotor.getClosedLoopError(0);
  }
  
  public void resetEncoder() {
    elevatorMotor.setSelectedSensorPosition(0, 0, TIMEOUT);
  }

  public boolean getTopSwitch() {
    return elevatorMotor.getSensorCollection().isFwdLimitSwitchClosed();
  }

  public boolean getBotSwitch() {
    return elevatorMotor.getSensorCollection().isRevLimitSwitchClosed();
  }

  //MANUAL CONTROL
  public void setSpeed(double speed) {
    elevatorMotor.set(ControlMode.PercentOutput, speed);
  }
}
