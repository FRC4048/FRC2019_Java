package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.Ultrasonic;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class TestDiagSonar{

    @Test
    public void testSonarInitially() throws Exception {
        Ultrasonic mockUltasonic = Mockito.mock(Ultrasonic.class);

        DiagSonar classUnderTest = new DiagSonar("switch", mockUltasonic, true, 3.0, 12.0);

        when(mockUltasonic.getRangeInches()).thenReturn(7.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockUltasonic.getRangeInches()).thenReturn(14.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockUltasonic.getRangeInches()).thenReturn(1.0);
        Assert.assertTrue(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));
    }

    @Test
    public void testSonarAfterReset() throws Exception {
        Ultrasonic mockUltasonic = Mockito.mock(Ultrasonic.class);

        DiagSonar classUnderTest = new DiagSonar("switch", mockUltasonic, true, 3.0, 12.0);

        when(mockUltasonic.getRangeInches()).thenReturn(7.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockUltasonic.getRangeInches()).thenReturn(14.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockUltasonic.getRangeInches()).thenReturn(1.0);
        Assert.assertTrue(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        classUnderTest.reset();
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));
    }
}