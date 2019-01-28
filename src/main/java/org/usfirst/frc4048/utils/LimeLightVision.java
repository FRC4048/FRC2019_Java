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

    NetworkTable table;
    NetworkTableEntry tv;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry ts;
    NetworkTableEntry tl;

    public LimeLightVision() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        ts = table.getEntry("ts");
        tl = table.getEntry("tl");
    }

    LimeLightVision(boolean DO_NOT_USE_FOR_TESTING_ONLY) {

    }

    /**
     * Calculate and return the distances from the camera.
     * @return Camera distance calculated from the limelight, null if no target acquired
     */
    public CameraDistance getTargetDistance() {
        double validTarget = tv.getDouble(0.0);
        if ( validTarget != 1.0 ) {
            return null;
        }

        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);

        return calcCameraDistance(x, y);
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

    CameraDistance calcCameraDistance(double angleX, double angleY) {
        // Maths
        double forwardDistance = (TARGET_HEIGHT - CAMERA_HEIGHT) / Math.tan(Math.toRadians(CAMERA_ANGLE + angleY));
        double sidewaysDistance = forwardDistance * Math.tan(Math.toRadians(angleX));

        return new CameraDistance(forwardDistance, sidewaysDistance);
    }

}
