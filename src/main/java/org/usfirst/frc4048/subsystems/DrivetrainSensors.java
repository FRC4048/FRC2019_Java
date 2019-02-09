/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.limelight.LimelightToggle;
import org.usfirst.frc4048.commands.limelight.LimelightToggleStream;
import org.usfirst.frc4048.utils.AngleFinder;
import org.usfirst.frc4048.utils.CameraAngles;
import org.usfirst.frc4048.utils.CameraDistance;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import org.usfirst.frc4048.utils.LimeLightVision;
import org.usfirst.frc4048.utils.OpticalRangeFinder;
import org.usfirst.frc4048.utils.SmartShuffleboard;

/**
 * This subsystem holds the sensors the robot uses for navigation
 */
public class DrivetrainSensors extends Subsystem {

    private Ultrasonic ultrasonic;

    private LimeLightVision limelight;

    public DrivetrainSensors() {
        ultrasonic = new Ultrasonic(RobotMap.ALIGNMENT_ULTRASONIC_ID[0], RobotMap.ALIGNMENT_ULTRASONIC_ID[1]);
        ultrasonic.setAutomaticMode(true);

        limelight = new LimeLightVision();
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

        if (RobotMap.SHUFFLEBOARD_DEBUG_MODE) {
            // Add commands:
            SmartShuffleboard.putCommand("DrivetrainSensors", "Limelight On", new LimelightToggle(true));
            SmartShuffleboard.putCommand("DrivetrainSensors", "Limelight Off", new LimelightToggle(false));
            SmartShuffleboard.putCommand("DrivetrainSensors", "Limelight Stream Toggle", new LimelightToggleStream());

            // Add other fields:
            SmartShuffleboard.put("DrivetrainSensors", "Ultrasonic", getUltrasonicDistance());
            CameraDistance targetDistance = getTargetDistance();
            SmartShuffleboard.put("DrivetrainSensors", "LimelightValidTarget", targetDistance != null);
            if (targetDistance != null) {
                SmartShuffleboard.put("DrivetrainSensors", "LimelightForward", targetDistance.getForward());
                SmartShuffleboard.put("DrivetrainSensors", "LimelightSideways", targetDistance.getSideways());
            }
        }
        Robot.completed(this, "log");
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

    /* Control the Limelight streaming mode */
    public void setStream(double option) {
        limelight.setStream(option);
    }

    public double getStream() {
        return limelight.getStream();
    }
}
