package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class TestDiagEncoder {

    @Test
    public void testPotInitially() throws Exception {
        Encoder mockEncoder = Mockito.mock(Encoder.class);
        when(mockEncoder.get()).thenReturn(1);

        DiagEncoder classUnderTest = new DiagEncoder("encoder", 100, mockEncoder, true);

        when(mockEncoder.get()).thenReturn(51);
        Assert.assertFalse(classUnderTest.getDiagResult());

        when(mockEncoder.get()).thenReturn(-50);
        Assert.assertFalse(classUnderTest.getDiagResult());

        when(mockEncoder.get()).thenReturn(101);
        Assert.assertTrue(classUnderTest.getDiagResult());

        when(mockEncoder.get()).thenReturn(50);
        Assert.assertTrue(classUnderTest.getDiagResult());
    }

    @Test
    public void testEncoderAfterReset() throws Exception {
        Encoder mockEncoder = Mockito.mock(Encoder.class);
        when(mockEncoder.get()).thenReturn(1);

        DiagEncoder classUnderTest = new DiagEncoder("encoder", 100, mockEncoder, true);

        when(mockEncoder.get()).thenReturn(101);
        Assert.assertTrue(classUnderTest.getDiagResult());

        classUnderTest.reset();
        Assert.assertFalse(classUnderTest.getDiagResult());
    }
}
