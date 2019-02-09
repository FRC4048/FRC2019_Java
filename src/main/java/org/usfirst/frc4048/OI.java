/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048;

import org.usfirst.frc4048.commands.LogError;
import org.usfirst.frc4048.commands.cargo.CargoEjectGroup;
import org.usfirst.frc4048.commands.cargo.IntakeCargo;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveToPos;
import org.usfirst.frc4048.commands.hatchpanel.HatchPanelIntake;
import org.usfirst.frc4048.commands.hatchpanel.HatchPanelRelease;
import org.usfirst.frc4048.utils.ElevatorPosition;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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
  private Joystick manualController;
  private XboxController manualXboxController;

  private JoystickButton manualHatchTopRocket;
  private JoystickButton manualHatchMidRocket;
  private JoystickButton manualHatchBotRocket;
  private JoystickButton manualCargoTopRocket;
  private JoystickButton manualCargoMidRocket;
  private JoystickButton manualCargoBotRocket;
  private JoystickButton manualCargoCargoship;
  
  private JoystickButton cargoIntake;
  private JoystickButton cargoShoot;
  private JoystickButton hatchIntake;
  private JoystickButton hatchDropoff;
  
  private JoystickButton driveSwitchMode;

  public OI() {
    leftJoy = new Joystick(0);
    rightJoy = new Joystick(1);
    controller = new Joystick(2);
    manualController = new Joystick(3);
    manualXboxController = new XboxController(3);

    // Put all button inputs that are based off of the mechanism they are tied to in
    // this switch statement
    int mode = Robot.mechanicalMode.getMode();
    switch (mode) {
    case RobotMap.CARGO_RETURN_CODE:
      manualCargoTopRocket = new JoystickButton(manualController, RobotMap.XBOX_Y_BUTTON);
      manualCargoMidRocket = new JoystickButton(manualController, RobotMap.XBOX_X_BUTTON);
      manualCargoBotRocket = new JoystickButton(manualController, RobotMap.XBOX_A_BUTTON);
      manualCargoCargoship = new JoystickButton(manualController, RobotMap.XBOX_B_BUTTON);
      cargoIntake = new JoystickButton(manualController, RobotMap.XBOX_RIGHT_BUMPER);
      cargoShoot = new JoystickButton(manualController, RobotMap.XBOX_LEFT_BUMPER);

      manualCargoTopRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_HIGH));
      manualCargoMidRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_MID));
      manualCargoBotRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_LOW));
      manualCargoCargoship.whenPressed(new ElevatorMoveToPos(ElevatorPosition.CARGO_CARGOSHIP_POS));
      cargoIntake.whenPressed(new IntakeCargo());
      cargoShoot.whenPressed(new CargoEjectGroup());
      break;
    case RobotMap.HATCH_RETURN_CODE:
      manualHatchTopRocket = new JoystickButton(manualController, RobotMap.XBOX_Y_BUTTON);
      manualHatchMidRocket = new JoystickButton(manualController, RobotMap.XBOX_X_BUTTON);
      manualHatchBotRocket = new JoystickButton(manualController, RobotMap.XBOX_A_BUTTON);
      hatchIntake = new JoystickButton(manualController, RobotMap.XBOX_RIGHT_BUMPER);
      hatchDropoff = new JoystickButton(manualController, RobotMap.XBOX_LEFT_BUMPER);
      
      manualHatchTopRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_HIGH));
      manualHatchMidRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_MID));
      manualHatchBotRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_BOT));
      hatchIntake.whenPressed(new HatchPanelIntake());
      hatchDropoff.whenPressed(new HatchPanelRelease());
      break;
    }

    driveSwitchMode = new JoystickButton(rightJoy, 6);

    logError = new JoystickButton(controller, 3);
    logError.whenPressed(new LogError());
  }

  public Joystick getLeftJoy() {
    return leftJoy;
  }

  public Joystick getRightJoy() {
    return rightJoy;
  }

  public double getLeftTrigger() {
    return manualXboxController.getTriggerAxis(Hand.kLeft);
  }

  public double getRightTrigger() {
    return manualXboxController.getTriggerAxis(Hand.kRight);
  }
}
