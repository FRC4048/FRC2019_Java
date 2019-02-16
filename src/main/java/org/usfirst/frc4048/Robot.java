/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc4048.commands.UnCradleIntake;
import org.usfirst.frc4048.commands.climber.ClimbWinchManual;
import org.usfirst.frc4048.commands.climber.PistonTest;
// import org.usfirst.frc4048.commands.DriveTargetCenter;
// import org.usfirst.frc4048.commands.LimelightAlign;
import org.usfirst.frc4048.commands.drive.CentricModeToggle;
import org.usfirst.frc4048.commands.drive.DriveAlignGroup;
import org.usfirst.frc4048.commands.drive.DriveAlignPhase2;
import org.usfirst.frc4048.commands.drive.DriveAlignPhase3;
import org.usfirst.frc4048.commands.drive.DriveDistance;
import org.usfirst.frc4048.commands.drive.RotateAngle;
import org.usfirst.frc4048.commands.drive.RotateAngleForAlignment;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveToPos;
import org.usfirst.frc4048.commands.limelight.LimelightToggle;
import org.usfirst.frc4048.commands.limelight.LimelightToggleStream;
import org.usfirst.frc4048.subsystems.CargoSubsystem;
import org.usfirst.frc4048.subsystems.Climber;
import org.usfirst.frc4048.subsystems.CompressorSubsystem;
import org.usfirst.frc4048.subsystems.DriveTrain;
import org.usfirst.frc4048.subsystems.DrivetrainSensors;
import org.usfirst.frc4048.subsystems.Elevator;
import org.usfirst.frc4048.subsystems.HatchPanelSubsystem;
import org.usfirst.frc4048.subsystems.Pivot;
import org.usfirst.frc4048.subsystems.PowerDistPanel;
import org.usfirst.frc4048.utils.ElevatorPosition;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.MechanicalMode;
import org.usfirst.frc4048.utils.SmartShuffleboard;
import org.usfirst.frc4048.utils.Timer;
import org.usfirst.frc4048.utils.diagnostics.Diagnostics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private final static String LINE = "-----------------------------------";
  public static OI oi;
  public static DriveTrain drivetrain;
  public static Logging logging;
  public static PowerDistPanel pdp;
  public static CompressorSubsystem compressorSubsystem;
  public static DrivetrainSensors drivetrainSensors;
  public static Elevator elevator;
  public static CargoSubsystem cargoSubsystem;
  public static HatchPanelSubsystem hatchPanelSubsystem;
  public static Climber climber;
  public static Diagnostics diagnostics;
  public static MechanicalMode mechanicalMode;
  private final static Timer timer = new Timer(100);
  public static Pivot pivot;
  
  /**
   * Robot thread scheduler. Initialized with a static thread pool.
   * 
   * @See {@link #scheduleTask(Runnable, long)}
   * @See {@link #cancelAllTasks()}
   */
  private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
  private final static ArrayList<ScheduledFuture<?>> tasks = new ArrayList<ScheduledFuture<?>>(); 


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    cancelAllTasks();

    diagnostics = new Diagnostics();

    mechanicalMode = new MechanicalMode();
    int mode = mechanicalMode.getMode();
    // int mode = RobotMap.CARGO_RETURN_CODE;
    if (RobotMap.ENABLE_DRIVETRAIN) {
      drivetrain = new DriveTrain();
    }
    pdp = new PowerDistPanel();

    if (RobotMap.ENABLE_COMPRESSOR) {
      compressorSubsystem = new CompressorSubsystem();
    }
    drivetrainSensors = new DrivetrainSensors();
    if (RobotMap.ENABLE_ELEVATOR){
      elevator = new Elevator();
    }
    switch(mode){
      case RobotMap.CARGO_RETURN_CODE:
        if (RobotMap.ENABLE_CARGO_SUBSYSTEM) {
          cargoSubsystem = new CargoSubsystem();
        }
        break;
      case RobotMap.HATCH_RETURN_CODE:
        if (RobotMap.ENABLE_HATCH_PANEL_SUBSYSTEM) {
          hatchPanelSubsystem = new HatchPanelSubsystem();
        }
        break;
      default:
        break;
    }
    if (RobotMap.ENABLE_CLIMBER_SUBSYSTEM) {
      climber = new Climber();
    }

    if(RobotMap.ENABLE_PIVOT_SUBSYSTEM){
      pivot = new Pivot();
    }
    logging = new Logging();

    // OI must be initialized last
    oi = new OI();
//    SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
    logging.traceMessage(Logging.MessageLevel.INFORMATION,
				"---------------------------- Robot Disabled ----------------------------");
    // Robot.drivetrainSensors.ledOff();
    new LimelightToggle(false);
    Scheduler.getInstance().run();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    logging.setStartTime();
    commonInit("autonomousInit");
    if(RobotMap.ENABLE_BEGIN_MATCH_GROUPCOMMAND){
      Scheduler.getInstance().add(new UnCradleIntake());
    }
    //    logging.traceMessage(Logging.MessageLevel.INFORMATION,
    //				"---------------------------- Autonomous mode starting ----------------------------");
    //    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
//
//    Scheduler.getInstance().run();
    teleopPeriodic();
  }

  @Override
  public void teleopInit() {
      commonInit("teleopInit");
  }
  
  public void commonInit(final String loggingLabel) {
    logging.traceMessage(Logging.MessageLevel.INFORMATION, LINE, loggingLabel, LINE);
    logging.writeAllTitles();

    new LimelightToggle(true);

    if(RobotMap.ENABLE_DRIVETRAIN) {
      Robot.drivetrain.swerveDrivetrain.setModeField();
    }

    if (RobotMap.SHUFFLEBOARD_DEBUG_MODE) {
      putCommandsOnShuffleboard();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    timer.init("teleopPeriodic");
    logging.writeAllData();
    timer.completed(this, "log");
    
    Scheduler.getInstance().run();
    timer.completed(this, "Sched");

    if (RobotMap.LOG_PERIODIC_TIME > 0) {
      if (timer.total() >= RobotMap.LOG_PERIODIC_TIME) {
        final String details = timer.toString();
        if (RobotMap.LOG_PERIODIC_TIME_TO_CONSOLE) {
          System.out.println(details);
        }
        logging.traceMessage(Logging.MessageLevel.TIMER, details);
      }
    }
    timer.term();
  }

  @Override
  public void testInit() {
    diagnostics.reset();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    diagnostics.refresh();
    Scheduler.getInstance().run();
  }


  private void putCommandsOnShuffleboard() {
    if (RobotMap.ENABLE_CLIMBER_SUBSYSTEM) {
      SmartShuffleboard.putCommand("Climber", "Forward", new ClimbWinchManual(0.5));
      SmartShuffleboard.putCommand("Climber", "Backwards", new ClimbWinchManual(-0.5));
      SmartShuffleboard.putCommand("Climber", "Stop", new ClimbWinchManual(0.0));
      SmartShuffleboard.putCommand("Climber", "Piston Forward", new PistonTest(DoubleSolenoid.Value.kForward));
      SmartShuffleboard.putCommand("Climber", "Piston Rev", new PistonTest(DoubleSolenoid.Value.kReverse));
      SmartShuffleboard.putCommand("Climber", "Piston Off", new PistonTest(DoubleSolenoid.Value.kOff));
    }
    if (RobotMap.ENABLE_DRIVETRAIN) {
      SmartShuffleboard.putCommand("Drive", "drive distance 10", new DriveDistance(10, 0.3, 0.0, 0.0));
      SmartShuffleboard.putCommand("Drive", "rotate 0", new RotateAngle(0));
      SmartShuffleboard.putCommand("Drive", "DriveAlignGroup", new DriveAlignGroup());
      SmartShuffleboard.putCommand("Drive", "DriveAlignPhase2", new DriveAlignPhase2(0.3, 0.4, false));
      SmartShuffleboard.putCommand("Drive", "DriveAlignPhase3", new DriveAlignPhase3(0.25, false));
      SmartShuffleboard.putCommand("Drive", "Toggle Centric Mode", new CentricModeToggle());
      SmartShuffleboard.putCommand("Drive", "ROtate angle align", new RotateAngleForAlignment());
    }

    SmartShuffleboard.putCommand("DrivetrainSensors", "Limelight On", new LimelightToggle(true));
    SmartShuffleboard.putCommand("DrivetrainSensors", "Limelight Off", new LimelightToggle(false));
    SmartShuffleboard.putCommand("DrivetrainSensors", "Limelight Stream Toggle", new LimelightToggleStream());
    
    if (RobotMap.ENABLE_ELEVATOR) {
      SmartShuffleboard.putCommand("Elevator", "Hatch Rocket Bottom", new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_BOT));
      SmartShuffleboard.putCommand("Elevator", "Hatch Rocket Mid", new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_MID));
      SmartShuffleboard.putCommand("Elevator", "Hatch Rocket High", new ElevatorMoveToPos(ElevatorPosition.HATCH_ROCKET_HIGH));
      SmartShuffleboard.putCommand("Elevator", "Cargo Rocket Low", new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_LOW));
      SmartShuffleboard.putCommand("Elevator", "Cargo Rocket Mid", new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_MID));
      SmartShuffleboard.putCommand("Elevator", "Cargo Rocket High", new ElevatorMoveToPos(ElevatorPosition.CARGO_ROCKET_HIGH));
      SmartShuffleboard.putCommand("Elevator", "Cargo Intake Pos", new ElevatorMoveToPos(ElevatorPosition.CARGO_INTAKE_POS));
      SmartShuffleboard.putCommand("Elevator", "Cargo Rocket Low", new ElevatorMoveToPos(ElevatorPosition.CARGO_CARGOSHIP_POS));
    }

  }

	public static Timer timer() {
	  return timer;
	}

	static public void completed(final Object caller, final String work) {
		if (RobotMap.LOG_PERIODIC_TIME > 0)
			timer.completed(caller, work);
	}

	/**
	 * Schedule a Thread to run with a fixed delay between runs.
	 */
	static public void scheduleTask(final Runnable task, final long intervalMS) {
		tasks.add(executor.scheduleWithFixedDelay(task, 0, intervalMS, TimeUnit.MILLISECONDS));
	}

	/**
	 * Cancel all scheduled threads.
	 */
	private void cancelAllTasks() {
		for (final ScheduledFuture<?> task : tasks) {
			task.cancel(true);
		}
		tasks.removeAll(tasks);
	}

}
