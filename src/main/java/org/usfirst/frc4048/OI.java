/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048;

import org.usfirst.frc4048.commands.LogError;

import edu.wpi.first.wpilibj.Joystick;
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

  public OI() {
    leftJoy = new Joystick(0);
    rightJoy = new Joystick(1);
    controller = new Joystick(2);
    manualController = new Joystick(3);
   

    //Put all button inputs that are based off of the mechanism they are tied to in this switch statement
    int mode = Robot.mechanicalMode.getMode();
    switch(mode) {
      case RobotMap.CARGO_RETURN_CODE:
        break;
      case RobotMap.HATCH_RETURN_CODE:
        break;
    }
    logError = new JoystickButton(controller, 3);
    logError.whenPressed(new LogError());
  }

  public Joystick getLeftJoy() {
    return leftJoy;
  }
  public Joystick getRightJoy() {
    return rightJoy;
  }
}
