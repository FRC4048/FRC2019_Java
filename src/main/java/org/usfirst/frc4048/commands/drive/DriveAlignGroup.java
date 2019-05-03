/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.drive;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

import org.usfirst.frc4048.commands.extension.ExtensionMove;
import org.usfirst.frc4048.commands.limelight.LimelightToggle;

public class DriveAlignGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DriveAlignGroup() {
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
    addSequential(new LimelightToggle(true));
    addSequential(new ExtensionMove(false));
    addSequential(new RotateAngleForAlignment()); //STEP 1
    addSequential(new WaitCommand(0.3));
    // addParallel(new WaitForChildren());
    // addSequential(new DriveAlignPhase2(0.25, 0.4, false)); //STEP 2
    addSequential(new DriveAlignPhase3(0.28, true)); //STEP 3
    addSequential(new CentricModeRobot());
    addSequential(new LimelightToggle(false));

  }
}