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
import org.usfirst.frc4048.utils.MotorUtils;

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
  private final double ELEVATOR_DOWN_SCALE_FACTOR = 1.00;

  private final double ELEVATOR_CARGO_P = 10;
  private final double ELEVATOR_CARGO_I = 0;
  private final double ELEVATOR_CARGO_D = 3;
  private final double ELEVATOR_CARGO_F = 0;

  private final double ELEVATOR_HATCH_P = 10;
  private final double ELEVATOR_HATCH_I = 0;
  private final double ELEVATOR_HATCH_D = 3;
  private final double ELEVATOR_HATCH_F = 0;

  private final int ELEVATOR_ACCEL = 375/* 281 */; // RPM Of motor
  private final int ELEVATOR_CRUISE_VELOCITY = 375/* 281 */; // ^
  private double elevatorSetpoint;

  private double elevatorP;
  private double elevatorI;
  private double elevatorD;
  private double elevatorF;
  private final boolean CARGO_MODE = true;
  private final boolean HATCH_MODE = false;

  public Elevator() {

    elevatorMotor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_ID);
    elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    elevatorMotor.configNominalOutputForward(0, TIMEOUT);
    elevatorMotor.configNominalOutputReverse(0, TIMEOUT);
    elevatorMotor.configPeakOutputForward(ELEVATOR_UP_SCALE_FACTOR, TIMEOUT);
    elevatorMotor.configPeakOutputReverse(-ELEVATOR_DOWN_SCALE_FACTOR, TIMEOUT);
    elevatorMotor.setNeutralMode(NeutralMode.Brake);

    elevatorMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
    elevatorMotor.selectProfileSlot(0, 0);
    elevatorMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
    
    setPID(CARGO_MODE);
    elevatorMotor.config_kP(0, elevatorP, TIMEOUT);
    elevatorMotor.config_kI(0, elevatorI, TIMEOUT);
    elevatorMotor.config_kD(0, elevatorD, TIMEOUT);
    elevatorMotor.config_kF(0, elevatorF, TIMEOUT);
    elevatorMotor.configMotionAcceleration(ELEVATOR_ACCEL, TIMEOUT);
    elevatorMotor.configMotionCruiseVelocity(ELEVATOR_CRUISE_VELOCITY, TIMEOUT);
    elevatorMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevatorMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

    resetEncoder();
    elevatorSetpoint = getEncoder();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("elevator setpoint", elevatorSetpoint);
    moveElevator();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ElevatorMoveManual());
  }

  public void moveElevator() {
    double position = elevatorSetpoint;
    elevatorMotor.set(ControlMode.MotionMagic, (int) position);
  }

  public void elevatorToPosition(ElevatorPosition elevatorPosition) {
    elevatorSetpoint = elevatorPosition.getPosition();
  }

  public boolean elevatorAtPos(ElevatorPosition elevatorPosition) {
    double position = elevatorPosition.getPosition();
    return Math.abs(getError()) < ELEVATOR_POSITION_ERROR
        && Math.abs(getTrajectoryPos() - position) < ELEVATOR_POSITION_ERROR;
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

  public double getEncoder() {
    return elevatorMotor.getSelectedSensorPosition(0);
  }

  public boolean getTopSwitch() {
    return elevatorMotor.getSensorCollection().isFwdLimitSwitchClosed();
  }

  public boolean getBotSwitch() {
    return elevatorMotor.getSensorCollection().isRevLimitSwitchClosed();
  }

  public void stopElevator() {
    elevatorSetpoint = getEncoder();
  }

  // MANUAL CONTROL
  public void setSpeed(double speed) {
    elevatorMotor.set(ControlMode.PercentOutput, speed);
  }

  public void fineTune(double speed) {
    elevatorSetpoint += speed;
  }

  public void setPID(boolean isCargo) {
    if (isCargo) {
      elevatorP = ELEVATOR_CARGO_P;
      elevatorI = ELEVATOR_CARGO_I;
      elevatorD = ELEVATOR_CARGO_D;
      elevatorF = ELEVATOR_CARGO_F;
    } else {
      elevatorP = ELEVATOR_HATCH_P;
      elevatorI = ELEVATOR_HATCH_I;
      elevatorD = ELEVATOR_HATCH_D;
      elevatorF = ELEVATOR_HATCH_F;
    }
  }

}