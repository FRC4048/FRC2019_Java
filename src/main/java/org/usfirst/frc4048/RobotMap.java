/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
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

  public static final int FRONT_LEFT_DRIVE_MOTOR_ID = 6; 
  public static final int FRONT_RIGHT_DRIVE_MOTOR_ID = 5; 
  public static final int REAR_LEFT_DRIVE_MOTOR_ID = 7;
  public static final int REAR_RIGHT_DRIVE_MOTOR_ID = 8; 
  public static final int FRONT_LEFT_STEER_MOTOR_ID = 2; 
  public static final int FRONT_RIGHT_STEER_MOTOR_ID = 1; 
  public static final int REAR_LEFT_STEER_MOTOR_ID = 3; 
  public static final int REAR_RIGHT_STEER_MOTOR_ID = 4;

  public static final int SWERVE_DRIVE_ANALOG_INPUT_FRONT_RIGHT_ID = 0;
  public static final int SWERVE_DRIVE_ANALOG_INPUT_FRONT_LEFT_ID = 1;
  public static final int SWERVE_DRIVE_ANALOG_INPUT_REAR_RIGHT_ID = 3;
  public static final int SWERVE_DRIVE_ANALOG_INPUT_REAR_LEFT_ID = 2;

  public static final int SWERVE_DRIVE_ENCODER_A_ID = 5; 
  public static final int SWERVE_DRIVE_ENCODER_B_ID = 6;

  public static final int DRIVE_PIGEON_ID = 9;

  public static PowerDistributionPanel pdp;

	public static void init() {
    pdp = new PowerDistributionPanel(0);
  }
  
  
}
