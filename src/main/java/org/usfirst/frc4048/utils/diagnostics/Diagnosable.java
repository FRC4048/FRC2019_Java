package org.usfirst.frc4048.utils.diagnostics;

public interface Diagnosable {

    /**
     * A method called periodically that will test the diagnosable value and update hte display
     */
    public void refresh();

    /**
     * A method to reset the "tested" state of the diagnosable component to its initial state
     */
    public void reset();
}
