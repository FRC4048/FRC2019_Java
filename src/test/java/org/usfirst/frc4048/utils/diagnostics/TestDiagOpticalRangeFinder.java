package org.usfirst.frc4048.utils.diagnostics;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.usfirst.frc4048.utils.OpticalRangeFinder;

import static org.mockito.Mockito.*;

public class TestDiagOpticalRangeFinder{

    @Test
    public void testOpticalRangeFinderInitially() throws Exception {
        OpticalRangeFinder mockOpticalRangeFinder = Mockito.mock(OpticalRangeFinder.class);

        DiagOpticalRangeFinder classUnderTest = new DiagOpticalRangeFinder("Optical Range Finder", mockOpticalRangeFinder,3.0, 12.0);

        when(mockOpticalRangeFinder.getDistanceInInches()).thenReturn(7.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockOpticalRangeFinder.getDistanceInInches()).thenReturn(14.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockOpticalRangeFinder.getDistanceInInches()).thenReturn(1.0);
        Assert.assertTrue(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));
    }

    @Test
    public void testOpticalRangeFinderAfterReset() throws Exception {
        OpticalRangeFinder mockOpticalRangeFinder = Mockito.mock(OpticalRangeFinder.class);

        DiagOpticalRangeFinder classUnderTest = new DiagOpticalRangeFinder("Optical Range Finder", mockOpticalRangeFinder, 3.0, 12.0);

        when(mockOpticalRangeFinder.getDistanceInInches()).thenReturn(7.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockOpticalRangeFinder.getDistanceInInches()).thenReturn(14.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockOpticalRangeFinder.getDistanceInInches()).thenReturn(1.0);
        Assert.assertTrue(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        classUnderTest.reset();
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));
    }
}