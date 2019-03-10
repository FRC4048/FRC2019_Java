/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048;

import org.usfirst.frc4048.commands.CancelCommand;
import org.usfirst.frc4048.commands.LogError;
import org.usfirst.frc4048.commands.ScheduleBButton;
import org.usfirst.frc4048.commands.manipulator.ReleaseGamePieceScheduler;
import org.usfirst.frc4048.commands.climber.ClimbDropRamp;
import org.usfirst.frc4048.commands.drive.CentricModeToggle;
import org.usfirst.frc4048.commands.drive.DriveAlignGroup;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveScheduler;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveToPos;
import org.usfirst.frc4048.commands.limelight.LimelightBlink;
import org.usfirst.frc4048.commands.manipulator.hatchpanel.HatchPanelIntake;
import org.usfirst.frc4048.commands.manipulator.hatchpanel.HatchPanelRelease;
import org.usfirst.frc4048.commands.pivot.PivotGroup;
import org.usfirst.frc4048.commands.pivot.TogglePivot;
import org.usfirst.frc4048.triggers.LeftDPADTrigger;
import org.usfirst.frc4048.triggers.RightDPADTrigger;
import org.usfirst.frc4048.triggers.XboxTriggerRight;
import org.usfirst.frc4048.utils.ElevatorPosition;
import org.usfirst.frc4048.utils.WantedElevatorPosition;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
  private JoystickButton logError;
  private Joystick controller;
  private Joystick leftJoy;
  private Joystick rightJoy;
  private XboxController xboxController;
  private final XboxTriggerRight xboxTriggerRight;
  private final RightDPADTrigger rightDPADTrigger;
  private final LeftDPADTrigger leftDPADTrigger;

  private JoystickButton rocketLow;
  private JoystickButton rocketMid;
  private JoystickButton rocketHigh;
  private JoystickButton cargoShipHeight;

  private JoystickButton cargoCargoship;

  private JoystickButton hatchIntake;
  private JoystickButton gamePieceRelease;

  private JoystickButton alignWithVision;
  private JoystickButton cancelCommand;
  private JoystickButton driveSwitchMode;

  private JoystickButton dropRamp;

  public OI() {
    leftJoy = new Joystick(0);
    rightJoy = new Joystick(1);
    controller = new Joystick(2);
    xboxController = new XboxController(2);
    xboxTriggerRight = new XboxTriggerRight(xboxController);
    rightDPADTrigger = new RightDPADTrigger(xboxController);
    leftDPADTrigger = new LeftDPADTrigger(xboxController);

    if (RobotMap.ENABLE_PIVOT_SUBSYSTEM) {
      leftDPADTrigger.whenActive(new PivotGroup());
    }

    if (RobotMap.ENABLE_DRIVETRAIN) {
      alignWithVision = new JoystickButton(leftJoy, 6);
      alignWithVision.whenPressed(new DriveAlignGroup());

      driveSwitchMode = new JoystickButton(rightJoy, 11);
      driveSwitchMode.whenPressed(new CentricModeToggle());
    }

    logError = new JoystickButton(leftJoy, 6);
    logError.whenPressed(new LogError());
    
    cancelCommand = new JoystickButton(controller, RobotMap.XBOX_BACK_BUTTON);
    cancelCommand.whenPressed(new CancelCommand());
    if (RobotMap.ENABLE_MANIPULATOR) {
      hatchIntake = new JoystickButton(controller, RobotMap.XBOX_RIGHT_BUMPER);
      gamePieceRelease = new JoystickButton(controller, RobotMap.XBOX_LEFT_BUMPER);

      hatchIntake.whenPressed(new HatchPanelIntake());
      gamePieceRelease.whenPressed(new ReleaseGamePieceScheduler());
    }
    if (RobotMap.ENABLE_ELEVATOR) {
      rocketHigh = new JoystickButton(controller, RobotMap.XBOX_Y_BUTTON);
      rocketMid = new JoystickButton(controller, RobotMap.XBOX_X_BUTTON);
      rocketLow = new JoystickButton(controller, RobotMap.XBOX_A_BUTTON);
      cargoCargoship = new JoystickButton(controller, RobotMap.XBOX_B_BUTTON);

      rocketHigh.whenPressed(new ElevatorMoveScheduler(WantedElevatorPosition.ROCKET_HIGH));
      rocketMid.whenPressed(new ElevatorMoveScheduler(WantedElevatorPosition.ROCKET_MID));
      rocketLow.whenPressed(new ElevatorMoveScheduler(WantedElevatorPosition.ROCKET_LOW));
      cargoCargoship.whenPressed(new LimelightBlink());
      
    }

    if (RobotMap.ENABLE_CLIMBER_SUBSYSTEM) {
      dropRamp = new JoystickButton(controller, RobotMap.XBOX_RIGHT_STICK_PRESS);
      dropRamp.whenPressed(new ClimbDropRamp());
    }
  }

  public Joystick getLeftJoy() {
    return leftJoy;
  }

  public Joystick getRightJoy() {
    return rightJoy;
  }

  public double getXboxLeftJoystickY(){
    return xboxController.getY(Hand.kLeft);
  }

  public void doRumble() {
    xboxController.setRumble(RumbleType.kLeftRumble, 1);
		xboxController.setRumble(RumbleType.kRightRumble, 1);
  }

  public void stopRumble() {
    xboxController.setRumble(RumbleType.kLeftRumble, 0);
    xboxController.setRumble(RumbleType.kRightRumble, 0);
  }
  public double getRightJoyStickY() {
    return xboxController.getY(Hand.kRight);
  }

  public double getRightTrigger() {
    return xboxController.getTriggerAxis(Hand.kRight);
  }

  public double getLeftTrigger() {
    return xboxController.getTriggerAxis(Hand.kLeft);
  }
}
