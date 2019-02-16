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

        DiagSwitch classUnderTest = new DiagSwitch("switch", mockInput);

        when(mockInput.get()).thenReturn(true);
        Assert.assertFalse(classUnderTest.getDiagResult());

        when(mockInput.get()).thenReturn(false);
        Assert.assertTrue(classUnderTest.getDiagResult());
    }

    @Test
    public void testSwitchAfterReset() throws Exception {
        DigitalInput mockInput = Mockito.mock(DigitalInput.class);

        DiagSwitch classUnderTest = new DiagSwitch("switch", mockInput);

        when(mockInput.get()).thenReturn(true);
        Assert.assertFalse(classUnderTest.getDiagResult());
        when(mockInput.get()).thenReturn(false);
        Assert.assertTrue(classUnderTest.getDiagResult());

        classUnderTest.reset();
        Assert.assertFalse(classUnderTest.getDiagResult());
    }
}
