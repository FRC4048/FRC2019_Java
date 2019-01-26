package org.usfirst.frc4048.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLightVision {

    public static final int LED_ON = 3;
    public static final int LED_OFF = 1;

    public static final double CAMERA_HEIGHT = 45.5; // Inches, height of Limelight
    public static final double TARGET_HEIGHT = 36.5; // Inches, height of field target
    public static final double CAMERA_ANGLE = -14; // Degrees, angle that the camera is mounted at

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tv = table.getEntry("tv");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    NetworkTableEntry ts = table.getEntry("ts");
    NetworkTableEntry tl = table.getEntry("tl");


    private double distance;
    private double horizontal;

    public void visionTest() {
        boolean validTarget = tv.getBoolean(false);
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        double skew = ts.getDouble(0.0);
        double latency = tl.getDouble(0.0);

        // SmartDashboard.putBoolean("LimelightValidTarget", validTarget);
        // SmartDashboard.putNumber("LimelightX", x);
        // SmartDashboard.putNumber("LimelightY", y);
        // SmartDashboard.putNumber("LimelightArea", area);
        // SmartDashboard.putNumber("LimelightSkew", skew);
        // SmartDashboard.putNumber("LimelightLatency", latency);

        // Maths
        distance = (TARGET_HEIGHT - CAMERA_HEIGHT) / Math.tan(Math.toRadians(CAMERA_ANGLE + y));
        horizontal = distance * Math.tan(Math.toRadians(x));

        // SmartDashboard.putNumber("LimelightDistance", distance);
        // SmartDashboard.putNumber("Horizontal offset", horizontal);
    }

    public double getDistance() {
        visionTest();
        return distance;
    }
    
    public double getHorizontal() {
        return horizontal;
    }
    
    public double getAngle() {
        double angle = Math.atan(distance/horizontal);
        
        SmartDashboard.putNumber("angle", Math.toDegrees(angle));
        return angle;
    }

    /**
     * Calculate and return teh distances from the camera.
     * @return Camera distance calculated from teh limelight, null if no target acquired
     */
    public CameraDistance getTargetDistance() {
        double validTarget = tv.getDouble(0.0);
        if ( validTarget != 1.0 ) {
            return null;
        }

        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);

        // Maths
        double forwardDistance = (TARGET_HEIGHT - CAMERA_HEIGHT) / Math.tan(Math.toRadians(CAMERA_ANGLE + y));
        double sidewaysDistance = forwardDistance * Math.tan(Math.toRadians(x));

        return new CameraDistance(forwardDistance, sidewaysDistance);
    }

    /**
     * Calculate and return the camera detected angles
     * @return the X and Y angles detected by the camera, Null in case no target is acquired
     */
    public CameraAngles getCameraAngles() {
        double validTarget = tv.getDouble(0.0);
        if ( validTarget != 1.0 ) {
            return null;
        }

        return new CameraAngles(tx.getDouble(0.0), ty.getDouble(0.0));
    }

    public void setLedOn() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(LED_ON);
    }

    public void setLedOff() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(LED_OFF);
    }

}