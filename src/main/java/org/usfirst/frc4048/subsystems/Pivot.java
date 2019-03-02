/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.pivot.MovePivot;
import org.usfirst.frc4048.utils.SmartShuffleboard;
import org.usfirst.frc4048.utils.diagnostics.DiagSwitch;

import edu.wpi.first.wpilibj.DigitalInput;
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
  // public WPI_TalonSRX pivotMotor;
  private DigitalInput limitSwitchDeployed;
  private DigitalInput limitSwitchRetracted;
  public boolean pivotDeployed = false;
  public Pivot() {
    pivotMotor = new Spark(RobotMap.PIVOT_MOTOR_ID);
    // pivotMotor = new WPI_TalonSRX(7);
    limitSwitchDeployed = new DigitalInput(RobotMap.PIVOT_LIMIT_SWITCH_LEFT_ID);
    limitSwitchRetracted = new DigitalInput(RobotMap.PIVOT_LIMIT_SWITCH_RIGHT_ID);

    Robot.diagnostics.addDiagnosable(new DiagSwitch("Pivot forward switch", limitSwitchDeployed));
    Robot.diagnostics.addDiagnosable(new DiagSwitch("Pivot reverse switch", limitSwitchRetracted));

    LiveWindow.add(pivotMotor);
  }

  public void toggleState(){
    if (pivotDeployed == false){
      pivotDeployed = true;
    }
    else {
      pivotDeployed = false;
    }
  }

  @Override
  public void periodic() {
    if(RobotMap.SHUFFLEBOARD_DEBUG_MODE){
      SmartShuffleboard.put("Pivot", "deployed", pivotDeployed);
      SmartShuffleboard.put("Pivot", "fwd Switch", getDeployedSwitch());
      SmartShuffleboard.put("Pivot", "rev Switch", getRetractedSwitch());
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new MovePivot());
  }

  public void setSpeed(double speed) {
    pivotMotor.set(speed);
  }

  public boolean getDeployedSwitch() {
    return !limitSwitchDeployed.get();
  }

  public boolean getRetractedSwitch() {
    return !limitSwitchRetracted.get();
  }
}