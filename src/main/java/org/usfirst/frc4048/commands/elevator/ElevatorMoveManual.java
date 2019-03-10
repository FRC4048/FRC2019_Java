/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.commands.elevator;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorMoveManual extends Command {
    private final double JOYSTICK_MARGIN_VALUE = 0.10;
    private final int FINETUNE_PROPORTION = 150;//this will change

    public ElevatorMoveManual() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if(Math.abs(Robot.oi.getRightTrigger()) > JOYSTICK_MARGIN_VALUE) {
            Robot.elevator.fineTune(Robot.oi.getRightTrigger() * FINETUNE_PROPORTION);
        } else if(Math.abs(Robot.oi.getLeftTrigger()) > JOYSTICK_MARGIN_VALUE) {
            Robot.elevator.fineTune(Robot.oi.getLeftTrigger() * FINETUNE_PROPORTION * -1);
        } else {
            Robot.elevator.fineTune(0.0);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
