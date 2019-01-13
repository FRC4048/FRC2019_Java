package org.usfirst.frc4048.swerve.drive;

/**
 * A Utilities class that contains some useful methods
 */
public class SwerveUtils {

	public static double convertEncoderValue(double encoderValue, double gearRatio){
		double encPos = encoderValue;
		
		encPos /= gearRatio;
		encPos = encPos % 1;
		
		return encPos;
	}
	
	public static double convertAngle(double angle, double gearRatio){
		double encVal = angle;
		
		encVal += gearRatio;
		
		return encVal;
	}
}