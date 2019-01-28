/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Spark winch;
  private Solenoid rampPiston;

  public Climber() {
    winch = new Spark(RobotMap.WINCH_MOTOR_ID);
    rampPiston = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.CLIMBER_PISTONS_ID);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void extendPistons() {
    rampPiston.set(true);
  }

  public void retractPiston() {
    rampPiston.set(false);
  }

  public void controlWinch(double speed) {
    winch.set(speed);
  }
}
