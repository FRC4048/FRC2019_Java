/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Add your docs here.
 */
public class Pivot extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Spark pivotMotor;
  private DigitalInput limitSwitchLeft;
  private DigitalInput limitSwitchRight;
  private Solenoid lockPiston;
  public Pivot() {
    pivotMotor = new Spark(RobotMap.PIVOT_MOTOR_ID);
    limitSwitchLeft = new DigitalInput(RobotMap.PIVOT_LIMIT_SWITCH_LEFT_ID);
    limitSwitchRight = new DigitalInput(RobotMap.PIVOT_LIMIT_SWITCH_RIGHT_ID);
    lockPiston = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.PIVOT_PISTON_ID);
    LiveWindow.add(pivotMotor);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setSpeed(double speed) {
    pivotMotor.set(speed);
  }

  public boolean getLeftSwitch() {
    return limitSwitchLeft.get();
  }

  public boolean getRightSwitch() {
    return limitSwitchRight.get();
  }

  public void movePiston(boolean state) {
    lockPiston.set(state);
  }
}