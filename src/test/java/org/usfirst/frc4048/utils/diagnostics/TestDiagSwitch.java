package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.DigitalInput;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class TestDiagSwitch {

    @Test
    public void testSwitchInitially() throws Exception {
        DigitalInput mockInput = Mockito.mock(DigitalInput.class);

        DiagSwitch classUnderTest = new DiagSwitch("switch", mockInput, true);

        when(mockInput.get()).thenReturn(true);
        Assert.assertFalse(classUnderTest.getDiagResult(mockInput));

        when(mockInput.get()).thenReturn(false);
        Assert.assertTrue(classUnderTest.getDiagResult(mockInput));
    }

    @Test
    public void testSwitchAfterReset() throws Exception {
        DigitalInput mockInput = Mockito.mock(DigitalInput.class);

        DiagSwitch classUnderTest = new DiagSwitch("switch", mockInput, true);

        when(mockInput.get()).thenReturn(true);
        Assert.assertFalse(classUnderTest.getDiagResult(mockInput));
        when(mockInput.get()).thenReturn(false);
        Assert.assertTrue(classUnderTest.getDiagResult(mockInput));

        classUnderTest.reset();
        Assert.assertFalse(classUnderTest.getDiagResult(mockInput));
    }
}
