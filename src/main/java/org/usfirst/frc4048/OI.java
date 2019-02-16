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
import org.usfirst.frc4048.commands.drive.CentricModeToggle;
import org.usfirst.frc4048.commands.drive.DriveAlignGroup;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveToPos;
import org.usfirst.frc4048.commands.hatchpanel.HatchPanelIntake;
import org.usfirst.frc4048.commands.hatchpanel.HatchPanelRelease;
import org.usfirst.frc4048.commands.pivot.PivotMoveDeploy;
import org.usfirst.frc4048.commands.pivot.PivotMoveRetract;
import org.usfirst.frc4048.triggers.LeftDPADTrigger;
import org.usfirst.frc4048.triggers.RightDPADTrigger;
import org.usfirst.frc4048.triggers.XboxTriggerRight;
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
  private XboxController xboxController;
  private final XboxTriggerRight xboxTriggerRight;
  private final RightDPADTrigger rightDPADTrigger;
  private final LeftDPADTrigger leftDPADTrigger;

  private JoystickButton hatchTopRocket;
  private JoystickButton hatchMidRocket;
  private JoystickButton hatchBotRocket;
  private JoystickButton cargoTopRocket;
  private JoystickButton cargoMidRocket;
  private JoystickButton cargoBotRocket;
  private JoystickButton cargoCargoship;

  private JoystickButton cargoIntake;
  private JoystickButton cargoShoot;
  private JoystickButton hatchIntake;
  private JoystickButton hatchDropoff;

  private JoystickButton alignWithVision;

  private JoystickButton driveSwitchMode;

  public OI() {
    leftJoy = new Joystick(0);
    rightJoy = new Joystick(1);
    controller = new Joystick(2);
    xboxController = new XboxController(2);
    xboxTriggerRight = new XboxTriggerRight(xboxController);
    rightDPADTrigger = new RightDPADTrigger(xboxController);
    leftDPADTrigger = new LeftDPADTrigger(xboxController);

    // Put all button inputs that are based off of the mechanism they are tied to in
    // this switch statement
    int mode = Robot.mechanicalMode.getMode();
    switch (mode) {
    case RobotMap.CARGO_RETURN_CODE:
      cargoTopRocket = new JoystickButton(controller, RobotMap.XBOX_Y_BUTTON);
      cargoMidRocket = new JoystickButton(controller, RobotMap.XBOX_X_BUTTON);
      cargoBotRocket = new JoystickButton(controller, RobotMap.XBOX_A_BUTTON);
      cargoCargoship = new JoystickButton(controller, RobotMap.XBOX_B_BUTTON);
      cargoIntake = new JoystickButton(controller, RobotMap.XBOX_RIGHT_BUMPER);
      cargoShoot = new JoystickButton(controller, RobotMap.XBOX_LEFT_BUMPER);

      if (RobotMap.ENABLE_ELEVATOR) {
        xboxTriggerRight.whenActive(new ElevatorMoveToPos(ElevatorPosition.CARGO_INTAKE_POS));
        cargoTopRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_HIGH));
        cargoMidRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_MID));
        cargoBotRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_LOW));
        cargoCargoship.whenPressed(new ElevatorMoveToPos(ElevatorPosition.CARGO_CARGOSHIP_POS));
      }
      if (RobotMap.ENABLE_CARGO_SUBSYSTEM) {
        cargoIntake.whenPressed(new IntakeCargo());
        cargoShoot.whenPressed(new CargoEjectGroup());
      }
      break;
    case RobotMap.HATCH_RETURN_CODE:
      hatchTopRocket = new JoystickButton(controller, RobotMap.XBOX_Y_BUTTON);
      hatchMidRocket = new JoystickButton(controller, RobotMap.XBOX_X_BUTTON);
      hatchBotRocket = new JoystickButton(controller, RobotMap.XBOX_A_BUTTON);
      hatchIntake = new JoystickButton(controller, RobotMap.XBOX_RIGHT_BUMPER);
      hatchDropoff = new JoystickButton(controller, RobotMap.XBOX_LEFT_BUMPER);

      if (RobotMap.ENABLE_ELEVATOR) {
        hatchTopRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_HIGH));
        hatchMidRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_MID));
        hatchBotRocket.whenPressed(new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_BOT));
      }
      if (RobotMap.ENABLE_HATCH_PANEL_SUBSYSTEM) {
        hatchIntake.whenPressed(new HatchPanelIntake());
        hatchDropoff.whenPressed(new HatchPanelRelease());
      }
      break;
    }

    if (RobotMap.ENABLE_PIVOT_SUBSYSTEM) {
      leftDPADTrigger.whenActive(new PivotMoveRetract());
      rightDPADTrigger.whenActive(new PivotMoveDeploy());
    }

    if (RobotMap.ENABLE_DRIVETRAIN) {
      alignWithVision = new JoystickButton(controller, RobotMap.XBOX_START_BUTTON);
      alignWithVision.whenPressed(new DriveAlignGroup());

      driveSwitchMode = new JoystickButton(rightJoy, 6);
      driveSwitchMode.whenPressed(new CentricModeToggle());
    }

    logError = new JoystickButton(leftJoy, 6);
    logError.whenPressed(new LogError());

    if (RobotMap.ENABLE_CLIMBER_SUBSYSTEM) {

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
}
