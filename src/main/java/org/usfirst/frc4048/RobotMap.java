package org.usfirst.frc4048;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * Change the interface definition as needed to use one of the specific hardware
 * maps.
 */
<<<<<<< HEAD
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  public static final double SWERVE_DRIVE_ENCODER_DISTANCE_PER_TICK = 0.0942478739;

  //CAN ID
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
  public static final int PDP_CAN_ID = 0;
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

  //DIGITAL INPUTS
  public static final int CARGO_LIMIT_SWITCH_LEFT_ID = 1;
  public static final int CARGO_LIMIT_SWITCH_RIGHT_ID = 2;
  public static final int SWERVE_DRIVE_ENCODER_A_ID = 5; 
  public static final int SWERVE_DRIVE_ENCODER_B_ID = 6;

  //PWM
  public static final int WINCH_MOTOR_ID = 1;
=======
>>>>>>> 4b58116dc3c7ca11cfb00121f69e2c2371a62622

// public interface RobotMap extends RobotMapForTestbed {
public interface RobotMap extends RobotMapFor2018Robot {

<<<<<<< HEAD
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

  //PDP
  public static final int ELEVATOR_PDP = 13; //this will change
=======
  // Do not add constants here. Use one of the RobotMapForXXXXX classes to add
  // definitions for the specific hardware.
>>>>>>> 4b58116dc3c7ca11cfb00121f69e2c2371a62622
}
