/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.utils.CameraDistance;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * This subsystem holds the sensors the robot uses for navigation
 */
public class DrivetrainSensors extends Subsystem {

  private Ultrasonic ultrasonic;

  private NetworkTableEntry unltrasonicEntry = Shuffleboard.getTab("Approach").add("Ultrasonic", 0.0).getEntry();

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tv = table.getEntry("tv");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry ts = table.getEntry("ts");
  NetworkTableEntry tl = table.getEntry("tl");

  NetworkTableEntry validTargetEntry = Shuffleboard.getTab("Approach").add("LimelightValidTarget", 0.0).getEntry();
  NetworkTableEntry limelightXEntry = Shuffleboard.getTab("Approach").add("LimelightX", 0.0).getEntry();
  NetworkTableEntry LimelightYEntry = Shuffleboard.getTab("Approach").add("LimelightY", 0.0).getEntry();
  NetworkTableEntry forwardDistanceEntry = Shuffleboard.getTab("Approach").add("forwardDistance", 0.0).getEntry();
  NetworkTableEntry sidewaysDistanceEntry = Shuffleboard.getTab("Approach").add("sidewaysDistance", 0.0).getEntry();

  public static final double CAMERA_HEIGHT = 45.5; // Inches, height of Limelight
  public static final double TARGET_HEIGHT = 36.5; // Inches, height of field target
  public static final double CAMERA_ANGLE = -13.0; // Degrees, angle that the camera is mounted at

  public static final int LED_ON = 3;
  public static final int LED_OFF = 1;

  public DrivetrainSensors() {
    ultrasonic = new Ultrasonic(8, 9);
    ultrasonic.setAutomaticMode(true);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void periodic() {
    // Put code here to be run every loop
    unltrasonicEntry.setDouble(ultrasonic.getRangeInches());

  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void init() {
  }

  public double getUltrasonicDistance() {
    return ultrasonic.getRangeInches();
  }

  public CameraDistance getTargetDistance() {
      double validTarget = tv.getDouble(0.0);
      if ( validTarget != 1.0 ) {
          return null;
      }

      double x = tx.getDouble(0.0);
      double y = ty.getDouble(0.0);
      // double area = ta.getDouble(0.0);
      // double skew = ts.getDouble(0.0);
      // double latency = tl.getDouble(0.0);

      // SmartDashboard.putBoolean("LimelightValidTarget", validTarget);
      // SmartDashboard.putNumber("LimelightX", x);
      // SmartDashboard.putNumber("LimelightY", y);
      // SmartDashboard.putNumber("LimelightArea", area);
      // SmartDashboard.putNumber("LimelightSkew", skew);
      // SmartDashboard.putNumber("LimelightLatency", latency);

      // Maths
      double forwardDistance = (TARGET_HEIGHT - CAMERA_HEIGHT) / Math.tan(Math.toRadians(CAMERA_ANGLE + y));
      double sidewaysDistance = forwardDistance * Math.tan(Math.toRadians(x));

      validTargetEntry.setDouble(validTarget);
      limelightXEntry.setDouble(x);
      LimelightYEntry.setDouble(y);
      forwardDistanceEntry.setDouble(forwardDistance);
      sidewaysDistanceEntry.setDouble(sidewaysDistance);

      return new CameraDistance(forwardDistance, sidewaysDistance);
  }
  
  public double getHorizontalAngle() {
      double validTarget = tv.getDouble(0.0);
      if ( validTarget != 1.0 ) {
          return 0.0;
      }

      double x = tx.getDouble(0.0);

      return x;
  }

  public void ledOn() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(LED_ON);
  }

  public void ledOff() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(LED_OFF);
  }
}
