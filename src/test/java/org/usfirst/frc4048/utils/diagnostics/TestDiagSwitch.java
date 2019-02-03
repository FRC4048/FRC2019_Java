package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class TestDiagSwitch {

    @Test
    public void testSwitchInitially() throws Exception {
        DigitalInput mockInput = Mockito.mock(DigitalInput.class);
        ShuffleboardContainer mockTab = Mockito.mock(ShuffleboardContainer.class);
        ShuffleboardComponent mockWidget = Mockito.mock(ShuffleboardComponent.class);
        NetworkTableEntry mockEntry = Mockito.mock(NetworkTableEntry.class);

        when(mockTab.add(anyString(), anyBoolean())).thenReturn(mockWidget);
        when(mockWidget.getEntry()).thenReturn(mockEntry);

        DiagSwitch classUnderTest = new DiagSwitch("switch", mockInput, mockTab);

        when(mockInput.get()).thenReturn(true);
        classUnderTest.refresh();
        verify(mockEntry, calls(1)).setBoolean(false);
    }
}
