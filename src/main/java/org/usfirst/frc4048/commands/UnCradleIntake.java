// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package org.usfirst.frc4048.commands;

// import org.usfirst.frc4048.commands.elevator.ElevatorMoveToPos;
// import org.usfirst.frc4048.commands.pivot.PivotMoveDeploy;
// import org.usfirst.frc4048.utils.ElevatorPosition;

// import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;

// public class UnCradleIntake extends CommandGroup {
//   /**
//    * Add your docs here.
//    */
//   public UnCradleIntake() {
//     // Add Commands here:
//     // e.g. addSequential(new Command1());
//     // addSequential(new Command2());
//     // these will run in order.

//     // To run multiple commands at the same time,
//     // use addParallel()
//     // e.g. addParallel(new Command1());
//     // addSequential(new Command2());
//     // Command1 and Command2 will run in parallel.

//     // A command group will require all of the subsystems that each member
//     // would require.
//     // e.g. if Command1 requires chassis, and Command2 requires arm,
//     // a CommandGroup containing them would require both the chassis and the
//     // arm.
//     addSequential(new ElevatorMoveToPos(ElevatorPosition.START_MATCH_POS));//brings elevator to about 15 inches above start
//     addSequential(new PivotMoveDeploy());//deploys the pivot
//     addSequential(new WaitCommand(0.5));
//     addSequential(new ElevatorMoveToPos(ElevatorPosition.CARGO_INTAKE_POS));//brings it to bottom
//   }
// }
