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
    
    @Test
    public void testAngle() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(0), 0.001D);
    }

    @Test
    public void testAngle2() throws Exception {
        Assert.assertEquals(90.0D, classUnderTest.calculateAngle(90), 0.001D);
    }

    @Test
    public void testAngle3() throws Exception {
        Assert.assertEquals(61.25D, classUnderTest.calculateAngle(61.25), 0.001D);
    }

    @Test
    public void testAngle4() throws Exception {
        Assert.assertEquals(151.25D, classUnderTest.calculateAngle(151.25), 0.001D);
    }

    @Test
    public void testAngle5() throws Exception {
        Assert.assertEquals(270.0D, classUnderTest.calculateAngle(270), 0.001D);
    }

    @Test
    public void testAngle6() throws Exception {
        Assert.assertEquals(241.25D, classUnderTest.calculateAngle(241.25), 0.001D);
    }

    @Test
    public void testAngle7() throws Exception {
        Assert.assertEquals(331.25D, classUnderTest.calculateAngle(331.25), 0.001D);
    }
    @Test
    public void testAngle8() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(3.0), 0.001D);
    }
    
    @Test
    public void testAngle9() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(357), 0.001D);
    }

    @Test
    public void testAngle10() throws Exception {
        Assert.assertEquals(0.0D, classUnderTest.calculateAngle(-3), 0.001D);
    }
    
    @Test
    public void testAngle11() throws Exception {
        Assert.assertEquals(331.25D, classUnderTest.calculateAngle(312), 0.001D);
    }
    
    @Test
    public void testAngle12() throws Exception {
        Assert.assertEquals(241.25D, classUnderTest.calculateAngle(230), 0.001D);
    }
}
