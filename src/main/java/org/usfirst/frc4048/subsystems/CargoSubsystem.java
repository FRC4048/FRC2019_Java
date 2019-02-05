package org.usfirst.frc4048.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoSubsystem extends Subsystem {

    private WPI_TalonSRX intakeRoller;
    private DigitalInput rightLimit;
    private DigitalInput leftLimit;
    private DigitalInput opticalSensor;
    private Solenoid ejectPiston;

    public final double CARGO_INPUT_SPEED = -1.0;
    public final double CARGO_OUTPUT_SPEED = 1.0;

    public CargoSubsystem() {
        intakeRoller = new WPI_TalonSRX(RobotMap.CARGO_INTAKE_MOTOR_ID);
        leftLimit = new DigitalInput(RobotMap.CARGO_LIMIT_SWITCH_LEFT_ID);
        rightLimit = new DigitalInput(RobotMap.CARGO_LIMIT_SWITCH_RIGHT_ID);
        ejectPiston = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.CARGO_PISTON_ID);
        opticalSensor = new DigitalInput(RobotMap.CARGO_OPTICAL_SENSOR_ID);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public boolean leftLimitPressed() {
        return !leftLimit.get();
    }

    public boolean rightLimitPressed() {
        return !rightLimit.get();
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
        return intakeRoller.getOutputCurrent();
    }
}