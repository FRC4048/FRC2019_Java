/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.drive.RotateAngleForAlignment;

/**
 * Add your docs here.
 */
public class TestAngleCommand {
    private RotateAngleForAlignment classUnderTest;
    
    @Before
    public void init() {
        classUnderTest = new RotateAngleForAlignment();
    }
    

    /* Tests for HATCH rotations */

    @Test
    public void testAngle() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(0, false), 0.001D);
    }

    @Test
    public void testAngle2() throws Exception {
        Assert.assertEquals(90.0D, classUnderTest.calculateAngle(90, false), 0.001D);
    }

    @Test
    public void testAngle3() throws Exception {
        Assert.assertEquals(28.75D, classUnderTest.calculateAngle(28.75D, false), 0.001D);
    }

    @Test
    public void testAngle4() throws Exception {
        Assert.assertEquals(151.25D, classUnderTest.calculateAngle(151.25, false), 0.001D);
    }

    @Test
    public void testAngle5() throws Exception {
        Assert.assertEquals(270.0D, classUnderTest.calculateAngle(270, false), 0.001D);
    }

    @Test
    public void testAngle6() throws Exception {
        Assert.assertEquals(208.75D, classUnderTest.calculateAngle(208.75, false), 0.001D);
    }

    @Test
    public void testAngle7() throws Exception {
        Assert.assertEquals(331.25D, classUnderTest.calculateAngle(331.25, false), 0.001D);
    }
    @Test
    public void testAngle8() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(3.0, false), 0.001D);
    }
    
    @Test
    public void testAngle9() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(357, false), 0.001D);
    }

    @Test
    public void testAngle10() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(-3, false), 0.001D);
    }
    
    @Test
    public void testAngle11() throws Exception {
        Assert.assertEquals(331.25D, classUnderTest.calculateAngle(312, false), 0.001D);
    }
    
    @Test
    public void testAngle12() throws Exception {
        Assert.assertEquals(208.75, classUnderTest.calculateAngle(230, false), 0.001D);
    }

/* Tests for CARGO rotations */

    @Test
    public void testAngle13() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(0, true), 0.001D);
    }

    @Test
    public void testAngle14() throws Exception {
        Assert.assertEquals(180.0D, classUnderTest.calculateAngle(180, true), 0.001D);
    }

    @Test
    public void testAngle15() throws Exception {
        Assert.assertEquals(180.0D, classUnderTest.calculateAngle(-180, true), 0.001D);
    }

    @Test
    public void testAngle16() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(22, true), 0.001D);
    }

    @Test
    public void testAngle17() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(-22, true), 0.001D);
    }

    @Test
    public void testAngle18() throws Exception {
        Assert.assertEquals(90.0D, classUnderTest.calculateAngle(95, true), 0.001D);
    }

    @Test
    public void testAngle19() throws Exception {
        Assert.assertEquals(180.0D, classUnderTest.calculateAngle(170, true), 0.001D);
    }

    @Test
    public void testAngle20() throws Exception {
        Assert.assertEquals(180.0D, classUnderTest.calculateAngle(-190, true), 0.001D);
    }

    @Test
    public void testAngle21() throws Exception {
        Assert.assertEquals(270.0D, classUnderTest.calculateAngle(250, true), 0.001D);
    }

    @Test
    public void testAngle22() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(42, true), 0.001D);
    }
}
