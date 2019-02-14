package org.usfirst.frc4048.swerve.drive;

import org.usfirst.frc4048.utils.Timer;

/**
 * Base class for enclosure. Implements common behavior that helps with the
 * robot driving: - Move method that takes into account current position and
 * optimizes the movement to reduce angle rotation - Allows the wheel to make
 * full rotation (when reaching full rotation don't go back to 0, rather keep
 * rotation in same direction) This class uses abstract lower-level
 * implementations of setSpeed and setAngle to be implemented by
 * hardware-specific sub-classes
 */
public abstract class BaseEnclosure implements SwerveEnclosure {

    private String name;
	protected double gearRatio;
	protected final Timer timer;
	private int encPosition;

	
	public BaseEnclosure(String name, double gearRatio,  final Timer timer)
	{
		this.name = name;
		this.gearRatio = gearRatio;
		this.timer = timer;
	}
	
	@Override
	public void move(double speed, double angle)
	{
		int encPosition = getLastEncPosition();
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
		timer.completed(this, "setspeed");
		
		if(speed != 0.0) {
			setAngle(angle); 
	        timer.completed(this, "setangle");
		}
	}

	public String getName()
	{
		return name;
	}
	

    /**
     * Read the encoder position from the hardware. Cache the result which can
     * retrieved by {@link #getLastEncPosition()}
     */
    public void readEncPosition() {
        encPosition = getEncPosition();
    }
    
    /**
     * Return the last read encoder position from the hardware. Returns a cached value.
     */
    public int getLastEncPosition() {
        return encPosition;
    }
	
    /**
     * Get the encoder position from the hardware. Sometimes this operation is slow
     * and we should not be reading the value frequently or perhaps on the periodic
     * method.
     */
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
		
		if(diff > 0.25) return true;
		else return false;
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