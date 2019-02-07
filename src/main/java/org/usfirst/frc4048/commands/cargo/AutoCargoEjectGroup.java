/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.cargo;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCargoEjectGroup extends CommandGroup {
  /**
   * Similar to CargoEjectGroup, this group command ejects the cargo from the intake by spinning up the motor, and then
   * using a piston to eject the ball, then stopping all motors/retracting the piston. The way this differs from CargoEjectGroup
   * is how the command first checks to make sure that the robot is lined up correctly by making sure that at least one of the
   * limit switches is pressed. This version of the command would be best used in autonoumous commands, in order to ensure the robot
   * is lined up before ejecting its cargo.
   */
  public AutoCargoEjectGroup() {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
    addSequential(new EjectCargoStart());
    addSequential(new AutoEjectCargoEnd());
  }
}
