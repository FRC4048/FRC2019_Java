/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.utils.AngleFinder;
import org.usfirst.frc4048.utils.CameraAngles;
import org.usfirst.frc4048.utils.CameraDistance;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.utils.LimeLightVision;
import org.usfirst.frc4048.utils.OpticalRangeFinder;

/**
 * This subsystem holds the sensors the robot uses for navigation
 */
public class DrivetrainSensors extends Subsystem {

    private Ultrasonic ultrasonic;

    private LimeLightVision limelight;
    private final AngleFinder climberAngleFinder;

    private NetworkTableEntry unltrasonicEntry = Shuffleboard.getTab("DrivetrainSensors").add("Ultrasonic Distance", 0.0).getEntry();
    private NetworkTableEntry limelightValidTargetEntry = Shuffleboard.getTab("DrivetrainSensors").add("LimelightValidTarget", false).getEntry();
    private NetworkTableEntry limelightXEntry = Shuffleboard.getTab("DrivetrainSensors").add("LimelightX", 0.0).getEntry();
    private NetworkTableEntry LimelightYEntry = Shuffleboard.getTab("DrivetrainSensors").add("LimelightY", 0.0).getEntry();
    private NetworkTableEntry limelightForwardEntry = Shuffleboard.getTab("DrivetrainSensors").add("LimelightForward", 0.0).getEntry();
    private NetworkTableEntry limelightSidewaysEntry = Shuffleboard.getTab("DrivetrainSensors").add("LimelightSideways", 0.0).getEntry();

    public DrivetrainSensors() {
        ultrasonic = new Ultrasonic(RobotMap.ALIGNMENT_ULTRASONIC_ID[0], RobotMap.ALIGNMENT_ULTRASONIC_ID[1]);
        ultrasonic.setAutomaticMode(true);

        limelight = new LimeLightVision();
        climberAngleFinder = initClimberAngleFinder();
    }
    
    /**
     * Initialize the climber angle finder. Standalone function since it involves
     * multiple steps.
     */
    private static AngleFinder initClimberAngleFinder() {
      final AnalogInput leftRangeInput = new AnalogInput(RobotMap.CLIMBER_DISTANCE_SENSOR_LEFT_ID);
      final AnalogInput rightRangeInput = new AnalogInput(RobotMap.CLIMBER_DISTANCE_SENSOR_RIGHT_ID);
      final AnalogInput all[] = { leftRangeInput, rightRangeInput };
      for (AnalogInput x : all) {
        // These AnalogInput settings provided stable and fast results.
        x.setOversampleBits(RobotMap.CLIMBER_DISTANCE_SENSOR_OVERSAMPLE_BITS);
        x.setAverageBits(RobotMap.CLIMBER_DISTANCE_SENSOR_AVERAGE_BITS);
      }

      final OpticalRangeFinder leftRangeFinder = new OpticalRangeFinder(leftRangeInput);
      final OpticalRangeFinder rightRangeFinder = new OpticalRangeFinder(rightRangeInput);
      return new AngleFinder(leftRangeFinder, rightRangeFinder, RobotMap.INCHES_BETWEEN_CLIMBER_DISTANCE_SENSORS);
    }



    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        CameraDistance targetDistance = getTargetDistance();
        limelightValidTargetEntry.setBoolean(targetDistance != null);
        if (targetDistance != null) {
            limelightForwardEntry.setDouble(targetDistance.getForward());
            limelightSidewaysEntry.setDouble(targetDistance.getSideways());
        }

        // unltrasonicEntry.setDouble(ultrasonic.getRangeInches());
        SmartDashboard.putNumber("Ultrasonic", getUltrasonicDistance());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void init() {
    }

    public double getUltrasonicDistance() {
        return ultrasonic.getRangeInches();
    }

    public CameraDistance getTargetDistance() {
        return limelight.getTargetDistance();
    }

    public CameraAngles getCameraAngles() {
        return limelight.getCameraAngles();
    }

    public void ledOn() {
        limelight.setLedOn();
    }

    public void ledOff() {
        limelight.setLedOff();
    }
}
