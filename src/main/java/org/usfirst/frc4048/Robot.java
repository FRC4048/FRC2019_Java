/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.commands.DriveDistance;
import org.usfirst.frc4048.commands.DriveDistanceMaintainAngle;
import org.usfirst.frc4048.commands.LimelightAlign;
import org.usfirst.frc4048.commands.LimelightDriveDistance;
import org.usfirst.frc4048.commands.RotateAngle;
import org.usfirst.frc4048.subsystems.DriveTrain;
import org.usfirst.frc4048.utils.LimeLightVision;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private NetworkTableEntry pigeonEntry;
  
  public static OI oi;
  public static DriveTrain drivetrain;
  public static LimeLightVision limelight;


  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();


  

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    drivetrain = new DriveTrain();
    limelight = new LimeLightVision();
    //OI must be initilized last
    oi = new OI();
    SmartDashboard.putData("Auto mode", m_chooser);
    pigeonEntry = Shuffleboard.getTab("Drivetrain").add("Gyro Angle", Robot.drivetrain.getGyro()).getEntry();    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("forward distance", Robot.limelight.getDistance());
    SmartDashboard.putNumber("horizontal distance", Robot.limelight.getHorizontal());
 
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    Robot.drivetrain.swerveDrivetrain.setModeField();
    
    SmartDashboard.putData(new RotateAngle(90));
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
    SmartDashboard.putData(new DriveDistance(80, 0.1, 0.05, 0.0));
    SmartDashboard.putData(new LimelightDriveDistance(30, 0.25));
    SmartDashboard.putData(new LimelightAlign());
    SmartDashboard.putData(new DriveDistanceMaintainAngle(80, -0.4, 0.05));
    pigeonEntry.setValue(Robot.drivetrain.getGyro());

    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();
  }

}
