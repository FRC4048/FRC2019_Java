package org.usfirst.frc4048.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.SmartShuffleboard;
import org.usfirst.frc4048.utils.diagnostics.DiagOpticalSensor;
import org.usfirst.frc4048.utils.diagnostics.DiagSwitch;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoSubsystem extends Subsystem {

    private Spark intakeRoller;
    private DigitalInput opticalSensor;
    private Solenoid ejectPiston;

    public final double CARGO_INPUT_SPEED = -1.0;
    public final double CARGO_OUTPUT_SPEED = 1.0;

    public CargoSubsystem() {
        intakeRoller = new Spark(RobotMap.CARGO_MOTOR_ID);
        ejectPiston = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.CARGO_PISTON_ID);
        opticalSensor = new DigitalInput(15);

        Robot.diagnostics.addDiagnosable(new DiagOpticalSensor("Cargo Optical Sensor", opticalSensor));
    }

    public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(this.getClass()) {

		protected void addAll() {
            add("Optical Triggered", !opticalSensor.get());
            add("Cargo Speed", getCargoSpeed());
            add("Piston State", getCargoPiston());
		}
    };

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
  
      // Put code here to be run every loop
      if (RobotMap.SHUFFLEBOARD_DEBUG_MODE) {
        // PUT SHUFFLEBOARD CODE HERE
        SmartShuffleboard.put("Cargo", "Optical Triggered", !opticalSensor.get());
        Robot.completed(this, "shuf");
      }
    }

    public boolean cargoInIntake() {
        return !opticalSensor.get();
    }

    public void cargoInput() {
        intakeRoller.set(CARGO_INPUT_SPEED);
    }

    public void cargoOutput() {
        intakeRoller.set(CARGO_OUTPUT_SPEED);
    }

    public void cargoStop() {
        intakeRoller.set(0.0);
    }

    public double getCargoSpeed() {
        return intakeRoller.get();
    }

    public void cargoEject() {
        ejectPiston.set(true);
    }

    public void cargoRetract() {
        ejectPiston.set(false);
    }

    public boolean getCargoPiston(){
        return ejectPiston.get();
    }

    public double getCargoCurrent(){
        return Robot.pdp.getPDP().getCurrent(RobotMap.PDP_ID_CARGO_INTAKE);
    }
}