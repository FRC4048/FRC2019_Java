package org.usfirst.frc4048.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;

public class LimeLightVision {

    public static final int LED_ON = 3;
    public static final int LED_OFF = 1;
    
    NetworkTable table;
    NetworkTableEntry tv;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry ts;
    NetworkTableEntry tl;
    NetworkTableEntry stream;

    public LimeLightVision() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        ts = table.getEntry("ts");
        tl = table.getEntry("tl");
        stream = table.getEntry("stream");
    }

    LimeLightVision(boolean DO_NOT_USE_FOR_TESTING_ONLY) {

    }

    /**
     * Calculate and return the distances from the camera.
     * The calculated distances are in inches, as calculated by the triangulation of the vertical distance between the
     * camera and target and the detected angles.
     * forward is the "straight" distance from the camera to the target
     * sideways is the "lateral" distance from the camera to the target
     * @return Camera distance calculated from the limelight, null if no target acquired
     */
    public CameraDistance getTargetDistance() {
        double validTarget = tv.getDouble(0.0);
        if ( validTarget != 1.0 ) {
            return null;
        }

        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);

        Robot.gamePieceMode.setStateBasedOnSensor();
        if(Robot.gamePieceMode.getState()){
            return calcCameraDistance(x, y, RobotMap.TARGET_HEIGHT_CARGO, RobotMap.CAMERA_HEIGHT, RobotMap.CAMERA_ANGLE);
        } else {
            return calcCameraDistance(x, y, RobotMap.TARGET_HEIGHT_HATCH, RobotMap.CAMERA_HEIGHT, RobotMap.CAMERA_ANGLE);    
        }
    }

    /**
     * Calculate and return the camera detected angles
     * The angles are returned by the Limelight camera and are in degrees.
     * tx is the horizontal angle between the target center and the camera crosshairs
     * ty is the vertical angle between the target center and the camera crosshairs
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

    /**
     * The Limelight camera has 3 streaming modes if a second USB camera is plugged in.
     * 0 = both, side by side
     * 1 = Limelight stream with USB camera in the corner
     * 2 = USB camera stream with Limelight in the corner
     */
    public void setStream(double option) {
        if (option >= 0.0 && option <= 2.0)
        {
           stream.setDouble(option);
        }
    }

    public double getStream() {
        return stream.getDouble(0.0);
    }

    /**
     * Internal utility to calculate the distances, in inches, from the camera based off of the viewed angles
     */
    CameraDistance calcCameraDistance(double angleX, double angleY, double targetHeight, double cameraHeight, double cameraAngle) {
        double forwardDistance = (targetHeight - cameraHeight) / Math.tan(Math.toRadians(cameraAngle + angleY));
        double sidewaysDistance = forwardDistance * Math.tan(Math.toRadians(angleX));

        return new CameraDistance(forwardDistance, sidewaysDistance);
    }

}
