package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class TestDiagPot {

    @Test
    public void testPotInitially() throws Exception {
        AnalogPotentiometer mockPot = Mockito.mock(AnalogPotentiometer.class);

        DiagPot classUnderTest = new DiagPot("pot", 1.0, 2.0, mockPot);

        when(mockPot.get()).thenReturn(1.5);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockPot.get()).thenReturn(1.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        when(mockPot.get()).thenReturn(2.5);
        Assert.assertTrue(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));
    }

    @Test
    public void testPotAfterReset() throws Exception {
        AnalogPotentiometer mockPot = Mockito.mock(AnalogPotentiometer.class);

        DiagPot classUnderTest = new DiagPot("pot", 1.0, 2.0,mockPot);

        when(mockPot.get()).thenReturn(1.0);
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));
        when(mockPot.get()).thenReturn(2.0);
        Assert.assertTrue(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));

        classUnderTest.reset();
        Assert.assertFalse(classUnderTest.getDiagResult(classUnderTest.getSensorReading()));
    }
}
