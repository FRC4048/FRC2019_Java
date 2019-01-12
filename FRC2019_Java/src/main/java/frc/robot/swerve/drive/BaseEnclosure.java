package frc.robot.swerve.drive;

/**
 * Base class for enclosure. Implements common behavior that helps with the robot driving:
 * - Move method that takes into account current position and optimizes the movement to reduce angle rotation
 * - Allows the wheel to make full rotation (when reaching full rotation don't go back to 0, rather keep rotation in same direction)
 * This class uses abstract lower-level implementations of setSpeed and setAngle to be implemented by hardware-specific sub-classes
 */
public abstract class BaseEnclosure implements SwerveEnclosure {

    private String name;
	protected double gearRatio;
	
	public BaseEnclosure(String name, double gearRatio)
	{
		this.name = name;
		this.gearRatio = gearRatio;
	}
	
	@Override
	public void move(double speed, double angle)
	{
		int encPosition = getEncPosition();
		angle = convertAngle(angle, encPosition);
		
		if(shouldReverse(angle, encPosition))
		{
			if(angle < 0)
				angle += 0.5;
			else
				angle -= 0.5;
			
			speed *= -1.0;
		}
		
		setSpeed(speed);
		
		if(speed != 0.0) {
			setAngle(angle); 
		}
	}

	public String getName()
	{
		return name;
	}
	
	protected abstract int getEncPosition();
	
	protected abstract void setEncPosition(int encPosition);
	
	protected abstract void setSpeed(double speed);
	
	protected abstract void setAngle(double angle);
	
	private boolean shouldReverse(double wa, double encoderValue)
	{
		double ea = SwerveUtils.convertEncoderValue(encoderValue, gearRatio);
		
		if(wa < 0)	wa += 1;
		
		double longDiff = Math.abs(wa - ea);
		
		double diff = Math.min(longDiff, 1.0-longDiff);
		//SmartDashboard.putNumber("Encoder Difference", diff);
		
		if(diff > 0.25) return true;
		else return false;
		//return false;
	}
	
	private double convertAngle(double angle, double encoderValue)
	{
		double encPos = encoderValue / gearRatio;
		
		double temp = angle;
		temp += (int)encPos;
		
		encPos = encPos % 1;
		
		if((angle - encPos) >  0.5) temp -= 1;
		if((angle - encPos) < -0.5) temp += 1;
		
		return temp;
	}
}