package org.usfirst.frc4048.utils.diagnostics;

import com.ctre.phoenix.sensors.PigeonIMU;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class TestDiagPigeon {

    @Test
    public void testPigeonInitially() throws Exception {
        PigeonIMU mockPigeon = Mockito.mock(PigeonIMU.class);
        when(mockPigeon.getFusedHeading()).thenReturn(1.0);

        DiagPigeon classUnderTest = new DiagPigeon("pigeon", 100, mockPigeon);

        when(mockPigeon.getFusedHeading()).thenReturn(51.0);
        Assert.assertFalse(classUnderTest.getDiagResult());

        when(mockPigeon.getFusedHeading()).thenReturn(-50.0);
        Assert.assertFalse(classUnderTest.getDiagResult());

        when(mockPigeon.getFusedHeading()).thenReturn(101.0);
        Assert.assertTrue(classUnderTest.getDiagResult());

        when(mockPigeon.getFusedHeading()).thenReturn(50.0);
        Assert.assertTrue(classUnderTest.getDiagResult());
    }

    @Test
    public void testPigeonAfterReset() throws Exception {
        PigeonIMU mockPigeon = Mockito.mock(PigeonIMU.class);
        when(mockPigeon.getFusedHeading()).thenReturn(1.0);

        DiagPigeon classUnderTest = new DiagPigeon("pigeon", 100, mockPigeon);

        when(mockPigeon.getFusedHeading()).thenReturn(101.0);
        Assert.assertTrue(classUnderTest.getDiagResult());

        classUnderTest.reset();
        Assert.assertFalse(classUnderTest.getDiagResult());
    }
}
