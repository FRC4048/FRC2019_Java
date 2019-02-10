/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4048.subsystems.CargoSubsystem;
import org.usfirst.frc4048.subsystems.Climber;
import org.usfirst.frc4048.subsystems.CompressorSubsystem;
import org.usfirst.frc4048.subsystems.DriveTrain;
import org.usfirst.frc4048.utils.*;
import org.usfirst.frc4048.subsystems.HatchPanelSubsystem;
import org.usfirst.frc4048.commands.cargo.AutoCargoEjectGroup;
import org.usfirst.frc4048.commands.cargo.CargoEjectGroup;
import org.usfirst.frc4048.commands.cargo.IntakeCargo;
import org.usfirst.frc4048.commands.climber.ClimbWinchManual;
// import org.usfirst.frc4048.commands.DriveTargetCenter;
// import org.usfirst.frc4048.commands.LimelightAlign;
import org.usfirst.frc4048.commands.drive.*;
import org.usfirst.frc4048.commands.limelight.LimelightToggleStream;
import org.usfirst.frc4048.commands.limelight.LimelightToggle;
import org.usfirst.frc4048.commands.elevator.ElevatorMoveToPos;
import org.usfirst.frc4048.subsystems.DriveTrain;
import org.usfirst.frc4048.subsystems.PowerDistPanel;
import org.usfirst.frc4048.subsystems.DrivetrainSensors;
import org.usfirst.frc4048.subsystems.Elevator;
import org.usfirst.frc4048.utils.diagnostics.Diagnostics;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI oi;
  public static DriveTrain drivetrain;
  public static Logging logging;
  public static PowerDistPanel pdp;
  public static WorkQueue wq;
  public static double timeOfStart = 0;
  public static CompressorSubsystem compressorSubsystem;
  public static DrivetrainSensors drivetrainSensors;
  public static Elevator elevator;
  public static CargoSubsystem cargoSubsystem;
  public static HatchPanelSubsystem hatchPanelSubsystem;
  public static Climber climber;
  public static Diagnostics diagnostics;
  public static MechanicalMode mechanicalMode;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    mechanicalMode = new MechanicalMode();
    int mode = mechanicalMode.getMode();

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
        DriverStation.reportError("-----Unable to determine robot has the Hatch Panel or Cargo assembly mounted-----", true);
        break;
    }
    if (RobotMap.ENABLE_CLIMBER_SUBSYSTEM) {
      climber = new Climber();
    }
    diagnostics = new Diagnostics();

    // OI must be initialized last
    oi = new OI();
    SmartDashboard.putData("Auto mode", m_chooser);

    WorkQueue wq = new WorkQueue(512);
    logging = new Logging(100, wq);
    logging.startThread(); // Starts the logger
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
    // Robot.drivetrainSensors.ledOff();
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
//    m_autonomousCommand = m_chooser.getSelected();
//
//    /*
//     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
//     * switch(autoSelected) { case "My Auto": autonomousCommand = new
//     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
//     * ExampleCommand(); break; }
//     */
//
//    // schedule the autonomous command (example)
//    if (m_autonomousCommand != null) {
//      m_autonomousCommand.start();
//    }
//    
    teleopInit();
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
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

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
    timer.init();
    Scheduler.getInstance().run();
    timer.completed(this, "Sched");

    if (RobotMap.LOG_PERIODIC_TIME > 0) {
      if (timer.total() >= RobotMap.LOG_PERIODIC_TIME) {
        System.out.println(timer.toString());
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
    }
    if (RobotMap.ENABLE_DRIVETRAIN) {
      SmartShuffleboard.putCommand("Drive", "drive distance 10", new DriveDistance(10, 0.3, 0.0, 0.0));
      SmartShuffleboard.putCommand("Drive", "rotate 0", new RotateAngle(0));
      SmartShuffleboard.putCommand("Drive", "DriveAlignGroup", new DriveAlignGroup());
      SmartShuffleboard.putCommand("Drive", "DriveAlignPhase2", new DriveAlignPhase2(0.3, 0.4, false));
      SmartShuffleboard.putCommand("Drive", "DriveAlignPhase3", new DriveAlignPhase3(0.25, false));
      SmartShuffleboard.putCommand("Drive", "Toggle Centric Mode", new CentricModeToggle());
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

  public static class Timer {
    private boolean enabled;
		private final String id;
		private int last = 0;
		private final long time[];
    private final String info[];
    private final String caller[];
    private final int max;

		Timer(final int max, final String id) {
      this.enabled = false;
      this.last = 0;
      this.max = max-1;
      this.id = id;
      this.time = new long[max];
      this.info = new String[max];
      this.caller = new String[max];
    }
    
    public long total() {
      return time[last] - time[0];
    }

		public void completed(final Object caller, final String info) {
      if (enabled) {
        if (last < max) {
          last += 1;
          this.time[last] = System.currentTimeMillis();
          this.caller[last] = caller.getClass().getSimpleName();
          this.info[last] = info;
        }
        else {
          System.out.println(String.format("Timer Max=%d Last=%d", max, last));
        }
      }
    }

		private void init() {
      enabled = true;
			last = 0;
			time[last] = System.currentTimeMillis();
			info[last] = "start";
    }
    
    private void term() {
      enabled = false;
      last = 0;
    }

		public String toString()
		{
			final StringBuilder sb = new StringBuilder();
      sb.append(id).append(": ").append(total());
      String lastCaller = "";
			for (int i=1; i<=last; i++) {
        if (!lastCaller.equals(caller[i])) {
          sb.append(" [").append(caller[i]).append("]");
          lastCaller = caller[i];
        }
				sb.append(" ").append(info[i]).append("+").append(time[i]-time[i-1]);
			}
			return sb.toString();
		}
  }

  private final static Timer timer = new Timer(100, "teleop");
  static public void completed(final Object caller, final String work) {
    if (RobotMap.LOG_PERIODIC_TIME > 0)
      timer.completed(caller, work);
  }

}
