package org.usfirst.frc4048.utils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestLimelight {

    private static LimeLightVision classUnderTest;

    @BeforeClass
    public static void init() throws Exception {
        classUnderTest = new LimeLightVision(true);
    }

    @Test
    public void testCalcAngle1() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(0, 0);
        Assert.assertEquals(36.0970284, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(0.0, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle2() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(0, 10);
        Assert.assertEquals(128.7059963, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(0.0, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle3() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(0, -10);
        Assert.assertEquals(20.21433097, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(0.0, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle4() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(0, -45);
        Assert.assertEquals(5.407745571, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(0.0, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle5() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(0, -30);
        Assert.assertEquals(9.31977274, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(0.0, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle6() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(10, 0);
        Assert.assertEquals(36.0970284, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(6.364880031, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle7() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(10, 10);
        Assert.assertEquals(128.7059963, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(22.69433973, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle8() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(10, -10);
        Assert.assertEquals(20.21433097, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(3.564331946, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle9() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(10, -45);
        Assert.assertEquals(5.407745571, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(0.953531449, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle10() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(10, -30);
        Assert.assertEquals(9.31977274, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(1.643327403, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle11() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(-10, 0);
        Assert.assertEquals(36.0970284, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(-6.364880031, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle12() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(-10, 10);
        Assert.assertEquals(128.7059963, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(-22.69433973, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle13() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(-10, -10);
        Assert.assertEquals(20.21433097, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(-3.564331946, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle14() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(-10, -45);
        Assert.assertEquals(5.407745571, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(-0.953531449, cameraDistance.getSideways(), 0.0001);
    }

    @Test
    public void testCalcAngle15() throws Exception {
        CameraDistance cameraDistance = classUnderTest.calcCameraDistance(-10, -30);
        Assert.assertEquals(9.31977274, cameraDistance.getForward(), 0.0001);
        Assert.assertEquals(-1.643327403, cameraDistance.getSideways(), 0.0001);
    }
}
