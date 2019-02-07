package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc4048.utils.OpticalRangeFinder;

/**
 * The subsystem that handles diagnostics. This subsystem will hold the list of Diagnosables and make sure they go through
 * their lifecycle: refresh and reset.
 * Use addDiagnosable() to add new components to the list - NOTE: Make sure they are only added to the list ONCE in the
 * Robot's lifetime (e.g. in the subsystem constructor).
 */
public class Diagnostics extends Subsystem {

    public static final String SHUFFLEBOARD_TAB_NAME =  "Diagnostics";

    private ShuffleboardTab shuffleBoardTab;

    private List<Diagnosable> diagnosables;

    public Diagnostics() {
        super("Diagnostics");

        shuffleBoardTab = Shuffleboard.getTab(SHUFFLEBOARD_TAB_NAME);
        diagnosables = new ArrayList<>();

        // This simulates adding components by other subsystems...
        /*
        addDiagnosable(new DiagSwitch("Switch1", new DigitalInput(1), shuffleBoardTab));
        addDiagnosable(new DiagPot("Pot1", 0.1, 0.9, new AnalogPotentiometer(0), shuffleBoardTab));
        addDiagnosable(new DiagEncoder("Encoder1", 100, new Encoder(5,6), shuffleBoardTab));
        
        addDiagnosable(new DiagOpticalSensor("OpticalSensor1", new DigitalInput(2), shuffleBoardTab));
        addDiagnosable(new DiagOpticalRangeFinder("OpticalRangeFinder1", new OpticalRangeFinder(new AnalogInput(1)), shuffleBoardTab, 3.0, 12.0));
        addDiagnosable(new DiagSonar("Sonar1", new Ultrasonic(3, 4), shuffleBoardTab, 3.0, 12.0));
        */
    }

    @Override
    protected void initDefaultCommand() {
        // Do nothing
    }

    public void addDiagnosable(Diagnosable diagnosable) {
        diagnosables.add(diagnosable);
    }

    /**
     * Refresh the display with current values.
     * This would go through the range of components and test their state, and then refresh the shuffleboard display
     * This method should be called periodically (e.g. in test mode) to ensure the components are all tested
     */
    public void refresh() {
        diagnosables.forEach(Diagnosable::refresh);
    }

    /**
     * Reset the diagnostics to the initial values. This would have the effect of gettng all the diagnosable items to
     * their initial state (as if they were not tested yet)
     */
    public void reset() {
        diagnosables.forEach(Diagnosable::reset);
    }
}
