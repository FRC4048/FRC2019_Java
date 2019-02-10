/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.utils.AngleFinder;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.OpticalRangeFinder;
import org.usfirst.frc4048.utils.SmartShuffleboard;

/**
 * Add your docs here.
 */
public class NeoExampleSubsystem extends Subsystem {
  private final CANPIDController pidController;
  private final CANEncoder encoder;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private CANSparkMax winch;

  public NeoExampleSubsystem() {
    winch = new CANSparkMax(RobotMap.WINCH_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    winch.setIdleMode(CANSparkMax.IdleMode.kBrake);
    pidController = winch.getPIDController();
    encoder = winch.getEncoder();

    pidController.setP(0.1);
    pidController.setI(1e-4);
    pidController.setD(1);
    pidController.setD(1);
    pidController.setIZone(0);
    pidController.setFF(0);
    pidController.setOutputRange(-0.5, 0.5);
  }

  public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(this.getClass()) {

		protected void addAll() {
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
    if (RobotMap.SHUFFLEBOARD_DEBUG_MODE) {
      // PUT SHUFFLEBOARD CODE HERE
      SmartShuffleboard.put("Climber", "Encoder", encoder.getPosition());
      Robot.completed(this, "shuf");
    }
  }

  public void controlNeo(double speed) {
    winch.set(speed);
  } 

  public void setLocation(double location) {
    pidController.setReference(location, ControlType.kPosition);
  }
}
