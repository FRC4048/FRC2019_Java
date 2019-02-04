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
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4048.swerve.drive.CanTalonSwerveEnclosure;
import org.usfirst.frc4048.swerve.drive.SwerveDrive;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.drive.Drive;

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

  private AnalogInput analogInputFrontRight;
  private AnalogInput analogInputFrontLeft;
  private AnalogInput analogInputRearRight;
  private AnalogInput analogInputRearLeft;

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

  private final int FR_ZERO = 1057;//1037;
  private final int FL_ZERO = 1790;//1767;
  private final int RL_ZERO = 2455;//1456;//1456;
  private final int RR_ZERO = 1487;//2444;

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

  /* Reading gyro angle is relatively slow, anywhere between 4mSec and 20mSec.             */
  /* We will be reading it and storing it here in the periodic method and whoever needs    */
  /* whoever needs to use the gyro will get the stored value through the getGyro() method. */
  private final PigeonData pigeonData = new PigeonData();

  public DriveTrain() {
    driveFL = new WPI_TalonSRX(RobotMap.FRONT_LEFT_DRIVE_MOTOR_ID);
    driveFR = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_DRIVE_MOTOR_ID);
    driveRL = new WPI_TalonSRX(RobotMap.REAR_LEFT_DRIVE_MOTOR_ID);
    driveRR = new WPI_TalonSRX(RobotMap.REAR_RIGHT_DRIVE_MOTOR_ID);
    steerFL = new WPI_TalonSRX(RobotMap.FRONT_LEFT_STEER_MOTOR_ID);
    steerFR = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_STEER_MOTOR_ID);
    steerRL = new WPI_TalonSRX(RobotMap.REAR_LEFT_STEER_MOTOR_ID);
    steerRR = new WPI_TalonSRX(RobotMap.REAR_RIGHT_STEER_MOTOR_ID);
    pigeon = new PigeonIMU(RobotMap.DRIVE_PIGEON_ID);
    encoder = new Encoder(RobotMap.SWERVE_DRIVE_ENCODER_A_ID, RobotMap.SWERVE_DRIVE_ENCODER_B_ID);
    
    driveFL.setNeutralMode(NeutralMode.Brake);
    driveFR.setNeutralMode(NeutralMode.Brake);
    driveRL.setNeutralMode(NeutralMode.Brake);
    driveRR.setNeutralMode(NeutralMode.Brake);

    driveFL.setSafetyEnabled(true);
    driveFR.setSafetyEnabled(true);
    driveRL.setSafetyEnabled(true);
    driveRL.setSafetyEnabled(true);

    analogInputFrontLeft = new AnalogInput(RobotMap.SWERVE_DRIVE_ANALOG_INPUT_FRONT_LEFT_ID);
    analogInputFrontRight = new AnalogInput(RobotMap.SWERVE_DRIVE_ANALOG_INPUT_FRONT_RIGHT_ID);
    analogInputRearLeft = new AnalogInput(RobotMap.SWERVE_DRIVE_ANALOG_INPUT_REAR_LEFT_ID);
    analogInputRearRight = new AnalogInput(RobotMap.SWERVE_DRIVE_ANALOG_INPUT_REAR_RIGHT_ID);

    frontLeftWheel = new CanTalonSwerveEnclosure("FrontLeftWheel", driveFL, steerFL, GEAR_RATIO);
    frontRightWheel = new CanTalonSwerveEnclosure("FrontRightWheel", driveFR, steerFR, GEAR_RATIO);
    rearLeftWheel = new CanTalonSwerveEnclosure("RearLeftWheel", driveRL, steerRL, GEAR_RATIO);
    rearRightWheel = new CanTalonSwerveEnclosure("RearRightWheel", driveRR, steerRR, GEAR_RATIO);

    init();

    swerveDrivetrain = new SwerveDrive(frontRightWheel, frontLeftWheel, rearLeftWheel, rearRightWheel, WIDTH, LENGTH);

    if (RobotMap.ENABLE_PIGEON_THREAD) {
      pigeonThread.start();
    }
  }
  
  public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.DRIVETRAIN) {

		protected void addAll() {
		}

    	
    
    };

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new Drive());
  }

  private final Thread pigeonThread = new Thread() {
    public void run() {
      for (;;) {
        pigeonData.update();
        // final double heading = pigeon.getFusedHeading();
        // final long headingAt = System.currentTimeMillis();
        // final long timeDiff = Math.abs(headingAt - pigeonCurrentAngleAt);
        // final double angleDiff = Math.abs(heading - pigeonCurrentAngle);
        // if (angleDiff > 0.3 && timeDiff < 5)
        //   System.out.println(String.format("pigeonThread: TimeDiff %d,   angleDiff %.1f", timeDiff, angleDiff));
      }
    }
  };

  private class PigeonData {
    private long time = 0;
    private double heading = 0;

    void update() {
      heading = pigeon.getFusedHeading();
      time = System.currentTimeMillis();  
    }
  }

  @Override
  public void periodic() {
    final long start = System.currentTimeMillis();

    // Put code here to be run every loop
    outputAbsEncValues();
    if (!RobotMap.ENABLE_PIGEON_THREAD) {
      pigeonData.update();
    }
    loggingContext.writeData();

    last_periodic = System.currentTimeMillis() - start;
  }
  public long last_periodic = -1;


  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void init() {
    pigeon.setYaw(0, TIMEOUT);
    pigeon.setFusedHeading(0, TIMEOUT);

    initSteerMotor(steerFR);
    initSteerMotor(steerFL);
    initSteerMotor(steerRL);
    initSteerMotor(steerRR);

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
    steerFR.setSelectedSensorPosition((int) ((analogInputFrontRight.getValue() - FR_ZERO) / 4000.0 * GEAR_RATIO), 0, TIMEOUT);
    steerFL.setSelectedSensorPosition((int) ((analogInputFrontLeft.getValue() - FL_ZERO) / 4000.0 * GEAR_RATIO), 0, TIMEOUT);
    steerRL.setSelectedSensorPosition((int) ((analogInputRearLeft.getValue() - RL_ZERO) / 4000.0 * GEAR_RATIO), 0, TIMEOUT);
    steerRR.setSelectedSensorPosition((int) ((analogInputRearRight.getValue() - RR_ZERO) / 4000.0 * GEAR_RATIO), 0, TIMEOUT);
    // steerFR.setSelectedSensorPosition(0);
    // steerFL.setSelectedSensorPosition(0);
    // steerRL.setSelectedSensorPosition(0);
    // steerRR.setSelectedSensorPosition(0);

    steerFR.set(ControlMode.Position, 0);
    steerFL.set(ControlMode.Position, 0);
    steerRL.set(ControlMode.Position, 0);
    steerRR.set(ControlMode.Position, 0);
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
    double angle = 0 - pigeonData.heading;
    return angle % 360;
  }

  public double getPitch() {
    double[] ypr = new double[3];
    pigeon.getYawPitchRoll(ypr);
    double pitch = ypr[1];
    return pitch;
  }

  /**
   * Outputs absolute encoder positions
   */
  public void outputAbsEncValues() {
    SmartDashboard.putNumber("Front Right Enc", steerFR.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Front Left Enc", steerFL.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Rear Left Enc", steerRL.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Rear Right Enc", steerRR.getSelectedSensorPosition(0));

    SmartDashboard.putNumber("Front Right Abs", analogInputFrontRight.getValue());
    SmartDashboard.putNumber("Front Left Abs", analogInputFrontLeft.getValue());
    SmartDashboard.putNumber("Rear Left Abs", analogInputRearLeft.getValue());
    SmartDashboard.putNumber("Rear Right Abs", analogInputRearRight.getValue());
  }

  /**
   * Turns wheels to reset position
   */
  public void setZero() {
    steerFR.set(ControlMode.Position, 0);
    steerFL.set(ControlMode.Position, 0);
    steerRL.set(ControlMode.Position, 0);
    steerRR.set(ControlMode.Position, 0);
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

    // TODO: Add Gyro Value from here to Drive.java
    swerveDrivetrain.move(fwd, str, rcw, getGyro());
  }

  public void stop() {
    swerveDrivetrain.stop();
  }
}
