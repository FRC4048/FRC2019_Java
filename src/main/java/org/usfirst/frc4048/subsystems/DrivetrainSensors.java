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
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.OpticalRangeFinder;
import org.usfirst.frc4048.utils.SmartShuffleboard;
import org.usfirst.frc4048.utils.diagnostics.DiagSonar;

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

        Robot.diagnostics.addDiagnosable(new DiagSonar("Drive Sonar", ultrasonic, 10, 20));
    }

    public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(this.getClass()) {

        protected void addAll() {
            add("Distance", getUltrasonicDistance());

            CameraDistance targetDistance = getTargetDistance();
            add("LimelightValidTarget", targetDistance != null);

            if(targetDistance != null) {
                CameraAngles angles = getCameraAngles();
                add("Camera Angle Tx", angles.getTx());
                add("Camera Angle Ty", angles.getTy());
                add("LimelightForward", targetDistance.getForward());
                add("LimelightSideways", targetDistance.getSideways());
            } else {
                add("Camera Angle Tx", "NO TARGET");
                add("Camera Angle Ty", "NO TARGET");
                add("LimelightForward", "NO TARGET");
                add("LimelightSideways", "NO TARGET");
            }
        }
    };

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        CameraDistance targetDistance = getTargetDistance();
        SmartShuffleboard.put("Driver", "LimelightValidTarget", targetDistance != null);

        if (RobotMap.SHUFFLEBOARD_DEBUG_MODE) {
            SmartShuffleboard.put("DrivetrainSensors", "Ultrasonic", getUltrasonicDistance());
            if (targetDistance != null) {
                SmartShuffleboard.put("DrivetrainSensors", "LimelightForward", targetDistance.getForward());
                SmartShuffleboard.put("DrivetrainSensors", "LimelightSideways", targetDistance.getSideways());
            }
            Robot.completed(this, "shuf");
        }
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
