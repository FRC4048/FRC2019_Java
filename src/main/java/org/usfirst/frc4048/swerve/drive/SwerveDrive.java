package org.usfirst.frc4048.swerve.drive;

import org.usfirst.frc4048.swerve.math.CentricMode;
import org.usfirst.frc4048.swerve.math.SwerveDirective;
import org.usfirst.frc4048.swerve.math.SwerveMath;

/**
 * The main class for the SwerveDrive subsystem: This class handles all aspects of controlling the swerve drive.
 * Use this class in your program if you want the easiest way to integrate swerve drive into your robot.
 */
public class SwerveDrive {
    // Enclosures 1-4 are the drive/steer combos
    private SwerveEnclosure swerveEnclosure1;
	private SwerveEnclosure swerveEnclosure2;
	private SwerveEnclosure swerveEnclosure3;
	private SwerveEnclosure swerveEnclosure4;
	
	private final SwerveMath swerveMath;
	
	private SwerveDirective[] swerveDirectives = {
			new SwerveDirective(), new SwerveDirective(), new SwerveDirective(), new SwerveDirective()
	};
	
	public SwerveDrive(SwerveEnclosure swerveEnclosure1,
					   SwerveEnclosure swerveEnclosure2,
					   SwerveEnclosure swerveEnclosure3,
					   SwerveEnclosure swerveEnclosure4,
					   double width, double length) 
	{
		this.swerveEnclosure1 = swerveEnclosure1;
		this.swerveEnclosure2 = swerveEnclosure2;
		this.swerveEnclosure3 = swerveEnclosure3;
		this.swerveEnclosure4 = swerveEnclosure4;
		
		swerveMath = new SwerveMath(width, length);
		
	}
	
	public void move(double fwd, double str, double rcw, Double gyroValue)
	{
		swerveMath.move(fwd, str, rcw, gyroValue, swerveDirectives);
		
		swerveEnclosure1.move(swerveDirectives[0].getSpeed(), swerveDirectives[0].getAngle());
		swerveEnclosure2.move(swerveDirectives[1].getSpeed(), swerveDirectives[1].getAngle());
		swerveEnclosure3.move(swerveDirectives[2].getSpeed(), swerveDirectives[2].getAngle());
		swerveEnclosure4.move(swerveDirectives[3].getSpeed(), swerveDirectives[3].getAngle());
	}
	
	public void stop()
	{
		swerveEnclosure1.stop();
		swerveEnclosure2.stop();
		swerveEnclosure3.stop();
		swerveEnclosure4.stop();
	}
	
	public void toggleMode()
	{
		this.swerveMath.toggleCentricMode();
	}
	
	public void setModeRobot() {
		this.swerveMath.setModeRobot();
	}
	
	public CentricMode getModeRobot() {
		return this.swerveMath.getCentricMode();
	}
	
	public void setModeField() {
		this.swerveMath.setModeField();
	}

}