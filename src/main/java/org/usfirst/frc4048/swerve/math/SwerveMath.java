package org.usfirst.frc4048.swerve.math;

/**
 * This is the main class for the swerveDrive library.
 * The library handles the calculations required to drive a robot using SwerveDrive wheels. These wheels have two motors:
 * A drive motor that moves the robot and a turn motor that changes the wheel assembly's direction.
 *
 * MOTOR LAYOUT
 *
 * 					Front
 * 		Wheel 2 -------------- Wheel 1
 * 			|					|
 * 			|					|
 * 			|					|
 *	Left 	|					|   Right
 * 			|					|
 * 			|					|
 * 			|					|
 * 		Wheel 3 -------------- Wheel 4
 * 					Back
 *
 * The library supports two modes: Robot centric and Field centric. In Robot centric mode the robot turns relative to its
 * current position: 45 degrees to the right will turn the robot 45 degrees to the right (for example, if it is pointing
 * north before the turn, it will point north-east after the turn. In Field centric mode the robot turns to face the given
 * number of degrees relative to the firld's orientation: 0 means straight ahead down the field, 90 means to the right, etc.
 */
public class SwerveMath {
    private final double length;
	private final double width;
	private final double diagonal;
	private final double SCALE_SPEED = 1.00;
/**
 * WARNING: if you change this variable to field centric it will mess with the autonomous!!!
 */
	private CentricMode centricMode = CentricMode.ROBOT;
	public void setModeRobot() {
		centricMode= CentricMode.ROBOT;
	}
	public void setModeField() {
		centricMode= CentricMode.FIELD;
	}

	public SwerveMath(double width, double length) {
		assert (width > 0) : "Width has to be larger than 0";
		assert (length > 0) : "Length has to be larger than 0";
		this.width = width;
		this.length = length;
		diagonal = Math.sqrt(Math.pow(this.length, 2) + Math.pow(this.width, 2));
	}

	public CentricMode getCentricMode() {
		return centricMode;
	}

	public void toggleCentricMode() {
		switch(centricMode) {
		case ROBOT:
			centricMode=CentricMode.FIELD;
			break;
		case FIELD:
			centricMode=CentricMode.ROBOT;
			break;
			}
	}

	public void move(double fwd, double str, double rcw, Double gyroValue, SwerveDirective[] result) {
		if ((gyroValue == null) && centricMode.equals(CentricMode.FIELD)) {
			throw new IllegalStateException("Cannot use field centric mode without a Gryo value"); 
		}
		if (isFieldCentric()){
			double gyro = (gyroValue * Math.PI) / 180;
			double temp = fwd * Math.cos(gyro) + str * Math.sin(gyro); 
			str = -fwd * Math.sin(gyro) + str * Math.cos(gyro); 
			fwd = temp; 
		} 
		double a = str - rcw*(length / diagonal); 
		double b = str + rcw*(length / diagonal); 
		double c = fwd - rcw*(length / diagonal); 
		double d = fwd + rcw*(length / diagonal); 
		
		double ws1 = Math.sqrt(Math.pow(b,2)+Math.pow(c,2)); 
		double ws2 = Math.sqrt(Math.pow(b,2)+Math.pow(d,2)); 
		double ws3 = Math.sqrt(Math.pow(a,2)+Math.pow(d,2)); 
		double ws4 = Math.sqrt(Math.pow(a,2)+Math.pow(c,2)); 
		
		double wa1 = Math.atan2(b,c)*180/Math.PI; 
		double wa2 = Math.atan2(b,d)*180/Math.PI; 
		double wa3 = Math.atan2(a,d)*180/Math.PI; 
		double wa4 = Math.atan2(a,c)*180/Math.PI; 
		
		double max = ws1; 
		if(ws2>max) max = ws2;
		if(ws3>max) max = ws3; 
		if(ws4>max) max = ws4; 
		if(max>1) {
			ws1/=max; 
			ws2/=max;
			ws3/=max; 
			ws4/=max;
		} 
		wa1/=360;
		wa2/=360;  
		wa3/=360;
		wa4/=360;
		ws1*=SCALE_SPEED; 
		ws2*=SCALE_SPEED; 
		ws3*=SCALE_SPEED; 
		ws4*=SCALE_SPEED; 
		
		setDirective(result[0], wa1, ws1);
		setDirective(result[1], wa2, ws2);
		setDirective(result[2], wa3, ws3);
		setDirective(result[3], wa4, ws4);

	}

	private void setDirective(SwerveDirective swerveDirective, double angle, double speed) {
		swerveDirective.setAngle(angle);
		swerveDirective.setSpeed(speed);
	}
	
	private boolean isFieldCentric() {
		return centricMode.equals(CentricMode.FIELD);
	}

}