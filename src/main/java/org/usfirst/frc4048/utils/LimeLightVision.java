package org.usfirst.frc4048.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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

    NetworkTableEntry validTargetEntry = Shuffleboard.getTab("Approach").add("LimelightValidTarget", 0.0).getEntry();
    NetworkTableEntry limelightXEntry = Shuffleboard.getTab("Approach").add("LimelightX", 0.0).getEntry();
    NetworkTableEntry LimelightYEntry = Shuffleboard.getTab("Approach").add("LimelightY", 0.0).getEntry();
    NetworkTableEntry forwardDistanceEntry = Shuffleboard.getTab("Approach").add("forwardDistance", 0.0).getEntry();
    NetworkTableEntry sidewaysDistanceEntry = Shuffleboard.getTab("Approach").add("sidewaysDistance", 0.0).getEntry();

    public static final double CAMERA_HEIGHT = 45.5; // Inches, height of Limelight
    public static final double TARGET_HEIGHT = 36.5; // Inches, height of field target
    public static final double CAMERA_ANGLE = -13.0; // Degrees, angle that the camera is mounted at

    public CameraDistance getTargetDistance() {
        double validTarget = tv.getDouble(0.0);
        if ( validTarget != 1.0 ) {
            return null;
        }

        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        // double area = ta.getDouble(0.0);
        // double skew = ts.getDouble(0.0);
        // double latency = tl.getDouble(0.0);

        // SmartDashboard.putBoolean("LimelightValidTarget", validTarget);
        // SmartDashboard.putNumber("LimelightX", x);
        // SmartDashboard.putNumber("LimelightY", y);
        // SmartDashboard.putNumber("LimelightArea", area);
        // SmartDashboard.putNumber("LimelightSkew", skew);
        // SmartDashboard.putNumber("LimelightLatency", latency);

        // Maths
        double forwardDistance = (TARGET_HEIGHT - CAMERA_HEIGHT) / Math.tan(Math.toRadians(CAMERA_ANGLE + y));
        double sidewaysDistance = forwardDistance * Math.tan(Math.toRadians(x));

        validTargetEntry.setDouble(validTarget);
        limelightXEntry.setDouble(x);
        LimelightYEntry.setDouble(y);
        forwardDistanceEntry.setDouble(forwardDistance);
        sidewaysDistanceEntry.setDouble(sidewaysDistance);

        return new CameraDistance(forwardDistance, sidewaysDistance);
    }
    
    public double getHorizontalAngle() {
        double validTarget = tv.getDouble(0.0);
        if ( validTarget != 1.0 ) {
            return 0.0;
        }

        double x = tx.getDouble(0.0);

        return x;
    }

}
