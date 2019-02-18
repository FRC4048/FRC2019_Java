package org.usfirst.frc4048.utils.diagnostics;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.usfirst.frc4048.swerve.drive.SparkMAXSwerveEnclosure;

import static org.mockito.Mockito.when;

public class TestDiagSwerveEnclosure {

    @Test
    public void testSwerveInitially() throws Exception {
        SparkMAXSwerveEnclosure mockEnclosure = Mockito.mock(SparkMAXSwerveEnclosure.class);
        when(mockEnclosure.getLastEncPosition()).thenReturn(1);

        DiagSwerveEnclosureSparkMAX classUnderTest = new DiagSwerveEnclosureSparkMAX("enclosure", 100, mockEnclosure);

        when(mockEnclosure.getLastEncPosition()).thenReturn(51);
        Assert.assertFalse(classUnderTest.getDiagResult());

        when(mockEnclosure.getLastEncPosition()).thenReturn(-50);
        Assert.assertFalse(classUnderTest.getDiagResult());

        when(mockEnclosure.getLastEncPosition()).thenReturn(101);
        Assert.assertTrue(classUnderTest.getDiagResult());

        when(mockEnclosure.getLastEncPosition()).thenReturn(50);
        Assert.assertTrue(classUnderTest.getDiagResult());
    }

    @Test
    public void testSwerveAfterReset() throws Exception {
        SparkMAXSwerveEnclosure mockEnclosure = Mockito.mock(SparkMAXSwerveEnclosure.class);
        when(mockEnclosure.getLastEncPosition()).thenReturn(1);

        DiagSwerveEnclosureSparkMAX classUnderTest = new DiagSwerveEnclosureSparkMAX("enclosure", 100, mockEnclosure);

        when(mockEnclosure.getLastEncPosition()).thenReturn(101);
        Assert.assertTrue(classUnderTest.getDiagResult());

        classUnderTest.reset();
        Assert.assertFalse(classUnderTest.getDiagResult());
    }
}
