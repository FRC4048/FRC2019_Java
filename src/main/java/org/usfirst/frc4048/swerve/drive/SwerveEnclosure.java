package org.usfirst.frc4048.swerve.drive;

/**
 * The interface representing an abstract Swerve Drive enclosure
 */
public interface SwerveEnclosure {
	
    String getName();
	
	void move(double speed, double angle);
	
	void stop();
}