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

    private Solenoid cargoPiston;

    public CargoSubsystem() {
        cargoPiston = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.CARGO_PISTON_ID);
    }

    public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(this.getClass()) {

		protected void addAll() {
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
        Robot.completed(this, "shuf");
      }
    }
    public void setPiston(boolean state) {
        cargoPiston.set(state);
    }
    public boolean getCargoPiston(){
        return cargoPiston.get();
    }
}