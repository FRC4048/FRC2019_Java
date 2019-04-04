/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.drive.Drive;
import org.usfirst.frc4048.swerve.drive.BaseEnclosure;
import org.usfirst.frc4048.swerve.drive.CanTalonSwerveEnclosure;
import org.usfirst.frc4048.swerve.drive.SparkMAXSwerveEnclosure;
import org.usfirst.frc4048.swerve.drive.SwerveDrive;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.SmartShuffleboard;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.FusionStatus;
import com.ctre.phoenix.sensors.PigeonIMU.GeneralStatus;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc4048.utils.diagnostics.DiagEncoder;
import org.usfirst.frc4048.utils.diagnostics.DiagSwerveEnclosureSparkMAX;
import org.usfirst.frc4048.utils.diagnostics.DiagSwerveEnclosureTalonSRX;

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

  /**
   * Note that access to the pigeon should be done inside a synhronized block
   * because the reading will be done in a separate thread. If you're reading
   * values, use the pigeonData class member.
   */
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
  private final double WIDTH = 18.1; // Remeasure Chassis

  // Length between drivetrain wheels
  private final double LENGTH = 23.75; // Remeasure Chassis

  private final boolean REVERSE_ENCODER = true;
  private final boolean REVERSE_OUTPUT = true;

  private final int FR_ZERO = 3534;//3315;
  private final int FL_ZERO = 180;//50;
  private final int RL_ZERO = 3340;//3575;
  private final int RR_ZERO = 150;//136;

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

  /* Reading gyro angle is relatively slow, anywhere between 4mSec and 20mSec. */
  /*
   * We will be reading it and storing it here in the periodic method and whoever
   * needs
   */
  /*
   * whoever needs to use the gyro will get the stored value through the getGyro()
   * method.
   */
  private final PigeonData pigeonData = new PigeonData();

  private final BaseEnclosure wheelEnclosures[];

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
    
    Robot.diagnostics.addDiagnosable(new DiagEncoder("DistanceEncoder", 1000, encoder));

    driveFL.setSafetyEnabled(true);
    driveFR.setSafetyEnabled(true);
    driveRL.setSafetyEnabled(true);
    driveRR.setSafetyEnabled(true);

    driveFL.setExpiration(0.5);
    driveFR.setExpiration(0.5);
    driveRL.setExpiration(0.5);
    driveRR.setExpiration(0.5);

    driveFR.setNeutralMode(NeutralMode.Brake);
    driveFL.setNeutralMode(NeutralMode.Brake);
    driveRR.setNeutralMode(NeutralMode.Brake);
    driveRL.setNeutralMode(NeutralMode.Brake);

    analogInputFrontLeft = new AnalogInput(RobotMap.SWERVE_DRIVE_ANALOG_INPUT_FRONT_LEFT_ID);
    analogInputFrontRight = new AnalogInput(RobotMap.SWERVE_DRIVE_ANALOG_INPUT_FRONT_RIGHT_ID);
    analogInputRearLeft = new AnalogInput(RobotMap.SWERVE_DRIVE_ANALOG_INPUT_REAR_LEFT_ID);
    analogInputRearRight = new AnalogInput(RobotMap.SWERVE_DRIVE_ANALOG_INPUT_REAR_RIGHT_ID);

    frontLeftWheel = new CanTalonSwerveEnclosure("FrontLeftWheel", driveFL, steerFL, GEAR_RATIO, Robot.timer());
    frontRightWheel = new CanTalonSwerveEnclosure("FrontRightWheel", driveFR, steerFR, GEAR_RATIO, Robot.timer());
    rearLeftWheel = new CanTalonSwerveEnclosure("RearLeftWheel", driveRL, steerRL, GEAR_RATIO, Robot.timer());
    rearRightWheel = new CanTalonSwerveEnclosure("RearRightWheel", driveRR, steerRR, GEAR_RATIO, Robot.timer());
    wheelEnclosures = new BaseEnclosure[] { frontLeftWheel, frontRightWheel, rearLeftWheel, rearRightWheel };

    Robot.diagnostics.addDiagnosable(new DiagSwerveEnclosureTalonSRX("FL Wheel", 1000, frontLeftWheel));
    Robot.diagnostics.addDiagnosable(new DiagSwerveEnclosureTalonSRX("FR Wheel", 1000, frontRightWheel));
    Robot.diagnostics.addDiagnosable(new DiagSwerveEnclosureTalonSRX("RL Wheel", 1000, rearLeftWheel));
    Robot.diagnostics.addDiagnosable(new DiagSwerveEnclosureTalonSRX("RR Wheel", 1000, rearRightWheel));

    if (RobotMap.ENABLE_WHEEL_ENCODER_THREAD) {
      Robot.scheduleTask(new WheelEncoderThread(), RobotMap.WHEEL_ENCODER_THREAD_INTERVAL_MS);
    }

    if (RobotMap.ENABLE_PIGEON_THREAD) {
      Robot.scheduleTask(new PigeonThread(), RobotMap.PIGEON_READ_DELAY_MS);
    }

    swerveDrivetrain = new SwerveDrive(frontRightWheel, frontLeftWheel, rearLeftWheel, rearRightWheel, WIDTH, LENGTH);
    
    init();

  }

  /**
   * Thread that reads all the wheel encoders at predetermined cycles on a
   * separate thread. The read cycle is determined by the value when the thread is
   * scheduled.
   */
  private class WheelEncoderThread implements Runnable {
    public void run() {
      for (final BaseEnclosure e : wheelEnclosures)
        e.readEncPosition();
    }
  }

  public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(this.getClass()) {

    protected void addAll() {
      add("FR Encoder", frontRightWheel.getLastEncPosition());
      add("FL Encoder", frontLeftWheel.getLastEncPosition());
      add("RR Encoder", rearRightWheel.getLastEncPosition());
      add("RL Encoder", rearLeftWheel.getLastEncPosition());

      add("FR Abs", analogInputFrontRight.getValue());
      add("FL Abs", analogInputFrontLeft.getValue());
      add("RR Abs", analogInputRearRight.getValue());
      add("RL Abs", analogInputRearLeft.getValue());

      add("Gyro", getGyro());
      add("Centric Mode", swerveDrivetrain.getModeRobot().name());
      add("Command Running", Robot.drivetrain.getCurrentCommandName());
    }
  };

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new Drive());
  }

  private class PigeonThread implements Runnable {
    public void run() {
      pigeonData.update();
    }
  };

  private class PigeonData {
    private final double[] ypr = new double[3];
    private final FusionStatus fstatus = new FusionStatus();
    private final GeneralStatus gstatus = new GeneralStatus();
    private long statusExpiration = 0;
    private double heading = 0;
    private double pitch = 0;

    void update() {
      synchronized (pigeon) {
        pigeon.getFusedHeading(fstatus);
        if (fstatus.bIsValid) {
          heading = fstatus.heading;
        } else {
          System.out.println("Pigeon reported invalid status");
          Robot.logging.traceMessage(Logging.MessageLevel.INFORMATION,
              "-------------Pigeon reported invalid status--------------");
        }
        pigeon.getYawPitchRoll(ypr);
        pitch = ypr[1];

        if (RobotMap.SHOW_PIGEON_STATUS_SECONDS > 0) {
          final long now = System.currentTimeMillis();
          if (now > statusExpiration) {
            statusExpiration = now + (RobotMap.SHOW_PIGEON_STATUS_SECONDS * 1000);
            pigeon.getGeneralStatus(gstatus);
            if (gstatus.lastError.value != 0) {
              System.out.println("PigeonStatus: " + gstatus.toString());
              Robot.logging.traceMessage(Logging.MessageLevel.INFORMATION, "PigeonStatus: " + gstatus.toString());
            }
          }
        }
      }
    }
  }

  @Override
  public void periodic() {

    if (!RobotMap.ENABLE_PIGEON_THREAD) {
      pigeonData.update();
      Robot.completed(this, "pigeon");
    }

    if (!RobotMap.ENABLE_WHEEL_ENCODER_THREAD) {
      for (final BaseEnclosure e : wheelEnclosures)
        e.readEncPosition();
      Robot.completed(this, "readenc");
    }

    if (RobotMap.SHUFFLEBOARD_DEBUG_MODE) {
      SmartShuffleboard.put("Drive", "Encoders", "FR", frontRightWheel.getLastEncPosition());
      SmartShuffleboard.put("Drive", "Encoders", "FL", frontLeftWheel.getLastEncPosition());
      SmartShuffleboard.put("Drive", "Encoders", "RR", rearRightWheel.getLastEncPosition());
      SmartShuffleboard.put("Drive", "Encoders", "RL", rearLeftWheel.getLastEncPosition());

      SmartShuffleboard.put("Drive", "Abs Encoders", "FR abs", analogInputFrontRight.getValue());
      SmartShuffleboard.put("Drive", "Abs Encoders", "FL abs", analogInputFrontLeft.getValue());
      SmartShuffleboard.put("Drive", "Abs Encoders", "RR abs", analogInputRearRight.getValue());
      SmartShuffleboard.put("Drive", "Abs Encoders", "RL abs", analogInputRearLeft.getValue());

      SmartShuffleboard.put("Drive", "Abs Encoder Voltage", "RR", analogInputRearRight.getVoltage());
      SmartShuffleboard.put("Drive", "Abs Encoder Voltage", "RL", analogInputRearLeft.getVoltage());

      SmartShuffleboard.put("Drive", "Gyro", getGyro());
      Robot.completed(this, "shuf");
    }
    SmartShuffleboard.put("Driver", "Centric mode", swerveDrivetrain.getModeRobot().name());
    SmartShuffleboard.put("Driver", "Gyro Value", getGyro());

  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void init() {
    setGyro(0);

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
    steerFR.setSelectedSensorPosition((int) ((analogInputFrontRight.getValue() - FR_ZERO) / 4000.0 * GEAR_RATIO), 0,
        TIMEOUT);
    steerFL.setSelectedSensorPosition((int) ((analogInputFrontLeft.getValue() - FL_ZERO) / 4000.0 * GEAR_RATIO), 0,
        TIMEOUT);
    steerRL.setSelectedSensorPosition((int) ((analogInputRearLeft.getValue() - RL_ZERO) / 4000.0 * GEAR_RATIO), 0,
        TIMEOUT);
    // steerRR.setSelectedSensorPosition((int) ((analogInputRearRight.getValue() - RR_ZERO) / 4000.0 * GEAR_RATIO), 0,
    //     TIMEOUT);
     
    // steerFR.setSelectedSensorPosition(0);
    // steerFL.setSelectedSensorPosition(0);
    // steerRL.setSelectedSensorPosition(0);
    steerRR.setSelectedSensorPosition(0);

    steerFR.set(ControlMode.Position, 0);
    steerFL.set(ControlMode.Position, 0);
    steerRL.set(ControlMode.Position, 0);
    // steerRR.set(ControlMode.Position, 0);
  }


  @SuppressWarnings("unused")
  public void setGyro(double angle) {
    synchronized (pigeon) {
      pigeon.setYaw(angle, TIMEOUT);
      pigeon.setFusedHeading(angle, TIMEOUT);
    }
  }

  public double getGyro() {
    double angle = 0 - pigeonData.heading;
    return angle % 360;
  }

  public double getPitch() {
    return pigeonData.pitch;
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


  public void move(double fwd, double str, double rcw) {
    if (fwd <= LEFT_JOY_X_MAX_DEADZONE && fwd >= LEFT_JOY_X_MIN_DEADZONE)
      fwd = 0.0;
    if (str <= LEFT_JOY_Y_MAX_DEADZONE && str >= LEFT_JOY_Y_MIN_DEADZONE)
      str = 0.0;
    if (rcw <= RIGHT_JOY_X_MAX_DEADZONE && rcw >= RIGHT_JOY_X_MIN_DEADZONE)
      rcw = 0.0;

    // TODO: Add Gyro Value from here to Drive.java
    swerveDrivetrain.move(-fwd, -str, -rcw, getGyro());
  }

  public void stop() {
    swerveDrivetrain.stop();
  }
}
