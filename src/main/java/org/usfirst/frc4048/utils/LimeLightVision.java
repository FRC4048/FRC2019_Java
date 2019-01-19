package org.usfirst.frc4048.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLightVision {
    /* Vision Testing */
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tv = table.getEntry("tv");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    NetworkTableEntry ts = table.getEntry("ts");
    NetworkTableEntry tl = table.getEntry("tl");

    public static final double CAMERA_HEIGHT = 49.5; // Inches, height of Limelight
    public static final double TARGET_HEIGHT = 36.5; // Inches, height of field target
    public static final double CAMERA_ANGLE = 0; // Degrees, angle that the camera is mounted at

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

}