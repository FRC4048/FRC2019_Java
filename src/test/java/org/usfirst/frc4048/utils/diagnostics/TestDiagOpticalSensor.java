package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.DigitalInput;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class TestDiagOpticalSensor{

    @Test
    public void testOpticalSensorInitially() throws Exception {
        DigitalInput mockInput = Mockito.mock(DigitalInput.class);

        DiagOpticalSensor classUnderTest = new DiagOpticalSensor("Optical Sensor", mockInput, true);

        when(mockInput.get()).thenReturn(true);
        Assert.assertFalse(classUnderTest.getDiagResult(mockInput));

        when(mockInput.get()).thenReturn(false);
        Assert.assertTrue(classUnderTest.getDiagResult(mockInput));
    }

    @Test
    public void testOpticalSensorAfterReset() throws Exception {
        DigitalInput mockInput = Mockito.mock(DigitalInput.class);

        DiagOpticalSensor classUnderTest = new DiagOpticalSensor("Optical Sensor", mockInput, true);

        when(mockInput.get()).thenReturn(true);
        Assert.assertFalse(classUnderTest.getDiagResult(mockInput));
        when(mockInput.get()).thenReturn(false);
        Assert.assertTrue(classUnderTest.getDiagResult(mockInput));

        classUnderTest.reset();
        Assert.assertFalse(classUnderTest.getDiagResult(mockInput));
    }
}