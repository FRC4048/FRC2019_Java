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

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveManual;
// import org.usfirst.frc4048.commands.elevator.ElevatorMoveManual;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveToPos;
import org.usfirst.frc4048.utils.ElevatorPosition;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.MechanicalMode;
import org.usfirst.frc4048.utils.MotorUtils;
import org.usfirst.frc4048.utils.SmartShuffleboard;
import org.usfirst.frc4048.utils.diagnostics.DiagEncoder;
import org.usfirst.frc4048.utils.diagnostics.DiagTalonSrxEncoder;
import org.usfirst.frc4048.utils.diagnostics.DiagTalonSrxSwitch;

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

  private final int ELEVATOR_POSITION_ERROR = 150;

  private final double ELEVATOR_UP_SCALE_FACTOR = 1.0;
  private final double ELEVATOR_DOWN_SCALE_FACTOR = 0.6;

  private final double ELEVATOR_P = 1;
  private final double ELEVATOR_I = 0.001;
  private final double ELEVATOR_D = 0.4;
  private final double ELEVATOR_F = 0;

  private double elevatorSetpoint;

  private double elevatorP;
  private double elevatorI;
  private double elevatorD;
  private double elevatorF;

  public Elevator() {

    elevatorMotor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_ID);
    elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    elevatorMotor.configNominalOutputForward(0, TIMEOUT);
    elevatorMotor.configNominalOutputReverse(0, TIMEOUT);
    elevatorMotor.configPeakOutputForward(ELEVATOR_UP_SCALE_FACTOR, TIMEOUT);
    elevatorMotor.configPeakOutputReverse(-ELEVATOR_DOWN_SCALE_FACTOR, TIMEOUT);
    elevatorMotor.setNeutralMode(NeutralMode.Brake);
    elevatorMotor.selectProfileSlot(0, 0);
    elevatorMotor.configAllowableClosedloopError(0, ELEVATOR_POSITION_ERROR, TIMEOUT);
    setPID();

    elevatorMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevatorMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevatorMotor.setInverted(true);

    Robot.diagnostics.addDiagnosable(new DiagTalonSrxSwitch("Elevator Forward Switch", elevatorMotor, DiagTalonSrxSwitch.Direction.FORWARD));
    Robot.diagnostics.addDiagnosable(new DiagTalonSrxSwitch("Elevator Reverse Switch", elevatorMotor, DiagTalonSrxSwitch.Direction.REVERSE));
  
    Robot.diagnostics.addDiagnosable(new DiagTalonSrxEncoder("Elevator Encoder", 100, elevatorMotor));


    resetEncoder();
    elevatorSetpoint = getEncoder();

  }

  public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(this.getClass()) {

    protected void addAll() {
      add("Top Switch", getTopSwitch());
      add("Bottom Switch", getBotSwitch());
      add("Setpoinnt", elevatorSetpoint);
      add("Encoder", getEncoder());
      add("Current", elevatorMotor.getOutputCurrent());
      add("PID Error", getError());
    }
  };

  @Override
  public void periodic() {
    moveElevator();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    // setDefaultCommand(new ElevatorMoveManual()); //ENABLE THIS IF NEEDED
  }

  public void moveElevator() {
    double position = elevatorSetpoint;
    elevatorMotor.set(ControlMode.Position, (int) position);
  }

  public void elevatorToPosition(ElevatorPosition elevatorPosition) {
    elevatorSetpoint = elevatorPosition.getPosition();
  }

  public boolean elevatorAtPos(ElevatorPosition elevatorPosition) {
    return Math.abs(getError()) < ELEVATOR_POSITION_ERROR;
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

  public void fineTune(double speed) {
    elevatorSetpoint += speed;
  }

  public void setPID() {
    elevatorP = ELEVATOR_P;
    elevatorI = ELEVATOR_I;
    elevatorD = ELEVATOR_D;
    elevatorF = ELEVATOR_F;

    elevatorMotor.config_kP(0, elevatorP, TIMEOUT);
    elevatorMotor.config_kI(0, elevatorI, TIMEOUT);
    elevatorMotor.config_kD(0, elevatorD, TIMEOUT);
    elevatorMotor.config_kF(0, elevatorF, TIMEOUT);
  }

  public WPI_TalonSRX getElevatorMotor() {
    return elevatorMotor;
  }
}
