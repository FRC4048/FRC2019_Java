/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048;


/**
 * This robot map defines hardware the hardware endpoints used by the software
 * to work with the 2018 robot and the wiring of it's actuators and sensors.
 * 
 * Do not use this class directly. Always use the RobotMap class to reference
 * these constants. That way we can quickly change which hardware the software
 * will use with a single change in RobotMap.
 */
interface RobotMapFor2018Robot {

  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  public static final boolean ENABLE_COMPRESSOR = false;

  /**
   * Enables reading of the gyro angle from the pigeon on it's own thread
   * so it does not impact the amount of time it takes to complete the
   * periodic() loop.
   */
  public static final boolean ENABLE_PIGEON_THREAD = true;

  public static final boolean ENABLE_DRIVETRAIN = true;

  public static final boolean ENABLE_ELEVATOR = false;

  public static final boolean ENABLE_CARGO_SUBSYSTEM = false; //this should be true when we actually get the robot

  public static final boolean ENABLE_HATCH_PANEL_SUBSYSTEM = false; //this should be true when we actually get the robot

  public static final boolean ENABLE_CLIMBER_SUBSYSTEM = false;

  /**
   * Enables loging of timing data related to watchdog timeouts.
   */
  public static final boolean LOG_PERIODIC_TIME = false;

  /**
  * Enable shuffleboard debug tabs
  */
  public static final boolean SHUFFLEBOARD_DEBUG_MODE = true;


  public static final double SWERVE_DRIVE_ENCODER_DISTANCE_PER_TICK = 0.0942478739;

  //CAN ID
  public static final int PDP_CAN_ID = 0;
  public static final int FRONT_LEFT_DRIVE_MOTOR_ID = 6; 
  public static final int FRONT_RIGHT_DRIVE_MOTOR_ID = 5; 
  public static final int REAR_LEFT_DRIVE_MOTOR_ID = 7;
  public static final int REAR_RIGHT_DRIVE_MOTOR_ID = 8; 
  public static final int FRONT_LEFT_STEER_MOTOR_ID = 2; 
  public static final int FRONT_RIGHT_STEER_MOTOR_ID = 1; 
  public static final int REAR_LEFT_STEER_MOTOR_ID = 3; 
  public static final int REAR_RIGHT_STEER_MOTOR_ID = 4;

  public static final int DRIVE_PIGEON_ID = 9;
  public static final int PCM_CAN_ID = 10;  
  public static final int ELEVATOR_MOTOR_ID = 11;
  public static final int CARGO_INTAKE_MOTOR_ID = 12;


  //ANALOG INPUTS
  public static final int SWERVE_DRIVE_ANALOG_INPUT_FRONT_RIGHT_ID = 0;
  public static final int SWERVE_DRIVE_ANALOG_INPUT_FRONT_LEFT_ID = 1;
  public static final int SWERVE_DRIVE_ANALOG_INPUT_REAR_RIGHT_ID = 3;
  public static final int SWERVE_DRIVE_ANALOG_INPUT_REAR_LEFT_ID = 2;
  
  public static final int CLIMBER_DISTANCE_SENSOR_LEFT_ID = 4;
  public static final int CLIMBER_DISTANCE_SENSOR_RIGHT_ID = 5;
  public static final int ALIGNMENT_DISTANCE_SENSOR_ID = 6;

  public static final int COMPRESSOR_PRESSURE_SENSOR = 7;

  //DIGITAL INPUTS
  public static final int CARGO_LIMIT_SWITCH_LEFT_ID = 1;
  public static final int CARGO_LIMIT_SWITCH_RIGHT_ID = 2;
  public static final int CARGO_OPTICAL_SENSOR_ID = 4;
  public static final int SWERVE_DRIVE_ENCODER_A_ID = 5; 
  public static final int SWERVE_DRIVE_ENCODER_B_ID = 6;
  public static final int[] ALIGNMENT_ULTRASONIC_ID = {3,4};
  public static final int HATCH_DIGITAL_INPUT_ID = 7;
  public static final int CARGO_DIGITAL_INPUT_ID = 8;
  //PWM
  public static final int WINCH_MOTOR_ID = 1;

  //PCM
  public static final int CARGO_PISTON_ID = 0;
  public static final int HATCH_PANEL_PISTON_ID = 1;
  public static final int HATCH_PANEL_WRIST_PISTON_ID = 2;
  public static final int INTAKES_WRIST_PISTON_ID = 3;
  public static final int CLIMBER_PISTONS_ID = 4;

  //OI
  public static final int XBOX_A_BUTTON = 1;
  public static final int XBOX_B_BUTTON = 2;
  public static final int XBOX_X_BUTTON = 3;
  public static final int XBOX_Y_BUTTON = 4;
  public static final int XBOX_LEFT_BUMPER = 5;
  public static final int XBOX_RIGHT_BUMPER = 6;
  public static final int XBOX_BACK_BUTTON = 7;
  public static final int XBOX_START_BUTTON = 8;
  public static final int XBOX_LEFT_STICK_PRESS = 9;
  public static final int XBOX_RIGHT_STICK_PRESS = 10;

  /**
   * In inches, the distance between the left and right optical sensors used for
   * aligning the robot for deploying the climbing ramp.
   * 
   * @see CLIMBER_DISTANCE_SENSOR_LEFT_ID
   * @see CLIMBER_DISTANCE_SENSOR_RIGHT_ID
   */
  public static final double INCHES_BETWEEN_CLIMBER_DISTANCE_SENSORS = 16.0;

  public static final int CLIMBER_DISTANCE_SENSOR_OVERSAMPLE_BITS = 14;

  public static final int CLIMBER_DISTANCE_SENSOR_AVERAGE_BITS = 0;

  public static final double CAMERA_HEIGHT = 43.5; // Inches, height of Limelight
  public static final double TARGET_HEIGHT_CARGO = 36.5; // Inches, height of field target
  public static final double TARGET_HEIGHT_HATCH = 32.0;
  public static final double CAMERA_ANGLE = -29.44; // Degrees, angle that the camera is mounted at

  public static final double ROBOT_CENTRIC_SCALE_RATIO = 0.3;

  public static final int HATCH_RETURN_CODE = 0;
  public static final int CARGO_RETURN_CODE = 1;
  public static final int NOTHING_RETURN_CODE = 2;
  
  /*
  * TODO Add correct values for 2018 test robot
  */
  //PDP IDS
  public static final int PDP_ID_CARGO_INTAKE = 13; //Just a test value for now; will be changed on the real robot.
  public static final int PDP_STEERING_FR = 11;
  public static final int PDP_STEERING_FL = 4;
  public static final int PDP_STEERING_RL = 5;
  public static final int PDP_STEERING_RR = 10;
  public static final int PDP_DRIVE_FR = 15;
  public static final int PDP_DRIVE_FL = 0;
  public static final int PDP_DRIVE_RL = 1;
  public static final int PDP_DRIVE_RR = 14;
  public static final int PDP_LIMELIGHT = 2;
  public static final int PDP_CLIMBER_WINCH = 3;
  public static final int PDP_ELEVATOR_MOTOR = 6;

  //MOTOR STALL CURRENT THRESHOLDS
  public static final double CURRENT_THRESHOLD_CARGO_INTAKE = 15.0;
}
