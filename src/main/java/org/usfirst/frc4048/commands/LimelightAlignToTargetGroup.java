/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.utils.CameraDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightAlignToTargetGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public LimelightAlignToTargetGroup() {
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
    final double maxSpeed = 0.5;
    final double minSpeed = 0.35;
    double forward;
    double horizontal;
    final double distanceAway = 20.0;
    CameraDistance targetDistance = Robot.drivetrainSensors.getTargetDistance();
    if(targetDistance == null) {
      forward = 0;  
      horizontal = 0;
    } else {
      forward = targetDistance.getForward();
      horizontal = targetDistance.getSideways();
    }

    final double angle = Math.toDegrees(Math.atan(horizontal/(forward-distanceAway)));
    final double moveDistance = Math.sqrt(Math.pow((forward-distanceAway), 2) + Math.pow(horizontal, 2));

    addSequential(new LimelightOn());
    addSequential(new RotateAngle(0));//step 1  THIS WILL CHANGE
    SmartDashboard.putNumber("move Distance", moveDistance);
    SmartDashboard.putNumber("angle", angle);
    // addSequential(new DriveDistanceMaintainAngle(moveDistance, angle, -maxSpeed, -minSpeed)); //step 2
    // addSequential(new DriveTargetCenter(distanceAway, -maxSpeed)); //step 3
    addSequential(new LimelightOff());
  }
}
 