package org.usfirst.frc4048.swerve.drive;

import org.usfirst.frc4048.utils.Timer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

/**
 * An implementation of the SwerveEnclosure using CanTalon motors and encoders
 */
public class CanTalonSwerveEnclosure extends BaseEnclosure implements SwerveEnclosure {

    private CANSparkMax driveMotor;
	private WPI_TalonSRX steerMotor;
	
	private boolean reverseEncoder = false;
	private boolean reverseSteer = false;
	
    public CanTalonSwerveEnclosure(String name, CANSparkMax driveMotor, WPI_TalonSRX steerMotor, double gearRatio,
           final Timer timer) {
        super(name, gearRatio, timer);
		this.driveMotor = driveMotor;
		this.steerMotor = steerMotor;
	}
	
	@Override
	public void stop()
	{
		this.steerMotor.stopMotor();
		this.driveMotor.stopMotor();
	}
	
	@Override
	public void setSpeed(double speed)
	{
		driveMotor.set(speed);
	}
	
	@Override
	public void setAngle(double angle)
	{
		steerMotor.set(ControlMode.Position, (reverseSteer ? -1 : 1) * angle * gearRatio);
		//steerMotor.enable();
	}
	
	@Override
	protected int getEncPosition()
	{
		int reverse = reverseEncoder ? -1 : 1;
		return reverse * steerMotor.getSelectedSensorPosition(0);
	}
	
	@Override
	public void setEncPosition(int position)
	{
		steerMotor.setSelectedSensorPosition(position, 0, 10);
	}
	
	public CANSparkMax getDriveMotor()
	{
		return driveMotor;
	}
	
	public WPI_TalonSRX getSteerMotor()
	{
		return steerMotor;
	}
	
	public boolean isReverseEncoder()
	{
		return reverseEncoder;
	}
	
	public void setReverseEncoder(boolean reverseEncoder)
	{
		this.reverseEncoder = reverseEncoder;
	}
	
	public void setReverseSteerMotor(boolean reverseSteer)
	{
		this.reverseSteer = reverseSteer;
	}
}