/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX driveFL;
  private WPI_TalonSRX driveFR;
  private WPI_TalonSRX driveRL;
  private WPI_TalonSRX driveRR;
  private WPI_TalonSRX steerFL;
  private WPI_TalonSRX steerFR;
  private WPI_TalonSRX steerRL;
  private WPI_TalonSRX steerRR;
  private PigeonIMU pigeon;
  private Encoder encoder;

  public SwerveDrive swerveDrivetrain;
  private CanTalonSwerveEnclosure frontLeftWheel;
  private CanTalonSwerveEnclosure frontRightWheel;
  private CanTalonSwerveEnclosure rearLeftWheel;
  private CanTalonSwerveEnclosure rearRightWheel;

  // Gear ratio of swerve wheels
  private final double GEAR_RATIO = (1988 / 1.2);

  // Width between drivetrain wheels
  private final double WIDTH = 22.0; // Remeasure Chassis

  // Length between drivetrain wheels
  private final double LENGTH = 23.25; // Remeasure Chassis

  private final boolean REVERSE_ENCODER = true;
  private final boolean REVERSE_OUTPUT = true;

  private final int FR_ZERO = 1028;
  private final int FL_ZERO = 1784;
  private final int RL_ZERO = 2475;
  private final int RR_ZERO = 1448;

  private final double P = 10;
  private final double I = 0;
  private final double D = 0;
  private final double F = 0;

  private final double LEFT_JOY_X_MIN_DEADZONE = -0.0234375;
  private final double LEFT_JOY_X_MAX_DEADZONE = 0.015748031437397003;
  private final double LEFT_JOY_Y_MIN_DEADZONE = -0.03125;
  private final double LEFT_JOY_Y_MAX_DEADZONE = 0.015748031437397003;
  private final double RIGHT_JOY_X_MIN_DEADZONE = -0.0078125;
  private final double RIGHT_JOY_X_MAX_DEADZONE = 0.031496062874794006;

  private final int TIMEOUT = 100;

  public DriveTrain() {
    driveFL = WPI_TalonSRX(RobotMap.FRONT_LEFT_DRIVE_MOTOR_ID);
    driveFR = WPI_TalonSRX(RobotMap.FRONT_RIGHT_DRIVE_MOTOR_ID);
    driveRL = WPI_TalonSRX(RobotMap.REAR_LEFT_DRIVE_MOTOR_ID);
    driveRR = WPI_TalonSRX(RobotMap.REAR_RIGHT_DRIVE_MOTOR_ID);
    steerFL = WPI_TalonSRX(RobotMap.FRONT_LEFT_STEER_MOTOR_ID);
    steerFR = WPI_TalonSRX(RobotMap.FRONT_RIGHT_STEER_MOTOR_ID);
    steerRL = WPI_TalonSRX(RobotMap.REAR_LEFT_STEER_MOTOR_ID);
    steerRR = WPI_TalonSRX(RobotMap.REAR_RIGHT_DRIVE_MOTOR_ID);
    pigeon = PigeonIMU(RobotMap.DRIVE_PIGEON_ID);
    encoder = Encoder(RobotMap.SWERVE_DRIVE_ENCODER_A_ID, RobotMap.SWERVE_DRIVE_ENCODER_B_ID);

    init();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void periodic() {
    // Put code here to be run every loop

    loggingContext.writeData();
  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void init() {
    pigeon.setYaw(0, TIMEOUT);
    pigeon.setFusedHeading(0, TIMEOUT);

    initSteerMotor(frontRightSteerMotor);
    initSteerMotor(frontLeftSteerMotor);
    initSteerMotor(rearLeftSteerMotor);
    initSteerMotor(rearRightSteerMotor);

    frontRightWheel.setReverseEncoder(REVERSE_ENCODER);
    frontLeftWheel.setReverseEncoder(REVERSE_ENCODER);
    rearLeftWheel.setReverseEncoder(REVERSE_ENCODER);
    rearRightWheel.setReverseEncoder(REVERSE_ENCODER);

    frontRightWheel.setReverseSteerMotor(REVERSE_OUTPUT);
    frontLeftWheel.setReverseSteerMotor(REVERSE_OUTPUT);
    rearLeftWheel.setReverseSteerMotor(REVERSE_OUTPUT);
    rearRightWheel.setReverseSteerMotor(REVERSE_OUTPUT);

    resetDriveEncoder();

    resetQuadEncoder();

    globalDriveDirSpeed = 0;
    globalDriveDistance = 0;
  }

  private void initSteerMotor(WPI_TalonSRX steerMotor) {
    steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);

    steerMotor.selectProfileSlot(0, 0); // Idx, Pdx

    // steerMotor.configEncoderCodesPerRev()
    steerMotor.configPeakOutputForward(1, TIMEOUT);
    steerMotor.configPeakOutputReverse(-1, TIMEOUT);

    steerMotor.configNominalOutputForward(0, TIMEOUT);
    steerMotor.configNominalOutputReverse(0, TIMEOUT);

    steerMotor.setNeutralMode(NeutralMode.Brake);

    steerMotor.configAllowableClosedloopError(0, 4, TIMEOUT);// arg0 = slotIdx, arg2 = timeoutMs

    steerMotor.config_kP(0, P, TIMEOUT);// 1st arg is slotIdx
    steerMotor.config_kI(0, I, TIMEOUT);
    steerMotor.config_kD(0, D, TIMEOUT);
    steerMotor.config_kF(0, F, TIMEOUT);
  }

  public void resetQuadEncoder() {
    frontRightSteerMotor.setSelectedSensorPosition(
        (int) ((getAnalogInputFrontRight().getValue() - FR_ZERO) / 4000.0 * GEAR_RATIO), 0, TIMEOUT);
    frontLeftSteerMotor.setSelectedSensorPosition(
        (int) ((analogInputFrontLeft.getValue() - FL_ZERO) / 4000.0 * GEAR_RATIO), 0, TIMEOUT);
    rearLeftSteerMotor.setSelectedSensorPosition(
        (int) ((analogInputRearLeft.getValue() - RL_ZERO) / 4000.0 * GEAR_RATIO), 0, TIMEOUT);
    rearRightSteerMotor.setSelectedSensorPosition(
        (int) ((analogInputRearRight.getValue() - RR_ZERO) / 4000.0 * GEAR_RATIO), 0, TIMEOUT);

    frontRightSteerMotor.set(ControlMode.Position, 0);
    frontLeftSteerMotor.set(ControlMode.Position, 0);
    rearLeftSteerMotor.set(ControlMode.Position, 0);
    rearRightSteerMotor.set(ControlMode.Position, 0);
  }

  public void resetDriveEncoder() {
    encoder.reset();
    encoder.setDistancePerPulse(RobotMap.SWERVE_DRIVE_ENCODER_DISTANCE_PER_TICK);
  }

  @SuppressWarnings("unused")
  private void setGyro(double angle) {
    pigeon.setYaw(angle, TIMEOUT);
    pigeon.setFusedHeading(angle, TIMEOUT);
  }

  public double getGyro() {
    double angle = 0 - pigeon.getFusedHeading();

    return angle % 360;
  }

  /**
   * Outputs absolute encoder positions
   */
  public void outputAbsEncValues() {
    SmartDashboard.putNumber("Front Right Enc", frontRightSteerMotor.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Front Left Enc", frontLeftSteerMotor.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Rear Left Enc", rearLeftSteerMotor.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Rear Right Enc", rearRightSteerMotor.getSelectedSensorPosition(0));

    SmartDashboard.putNumber("Front Right Abs", getAnalogInputFrontRight().getValue());
    SmartDashboard.putNumber("Front Left Abs", analogInputFrontLeft.getValue());
    SmartDashboard.putNumber("Rear Left Abs", analogInputRearLeft.getValue());
    SmartDashboard.putNumber("Rear Right Abs", analogInputRearRight.getValue());
  }

  /**
   * Turns wheels to reset position
   */
  public void setZero() {
    frontRightSteerMotor.set(ControlMode.Position, 0);
    frontLeftSteerMotor.set(ControlMode.Position, 0);
    rearLeftSteerMotor.set(ControlMode.Position, 0);
    rearRightSteerMotor.set(ControlMode.Position, 0);
  }

  public double getDistance() {
    return encoder.getDistance();
  }

  /**
   * Outputs direction of encoder
   * 
   * @return - true if encoder direction is positive, false if encoder direction
   *         is negative
   */
  public boolean getEncoderDirection() {
    return encoder.getDirection();
  }

  public void move(double fwd, double str, double rcw) {
    if (fwd <= LEFT_JOY_X_MAX_DEADZONE && fwd >= LEFT_JOY_X_MIN_DEADZONE)
      fwd = 0.0;
    if (str <= LEFT_JOY_Y_MAX_DEADZONE && str >= LEFT_JOY_Y_MIN_DEADZONE)
      str = 0.0;
    if (rcw <= RIGHT_JOY_X_MAX_DEADZONE && rcw >= RIGHT_JOY_X_MIN_DEADZONE)
      rcw = 0.0;

    swerveDrivetrain.move(fwd, str, rcw, getGyro());
  }

  public void stop() {
    swerveDrivetrain.stop();
  }
}
