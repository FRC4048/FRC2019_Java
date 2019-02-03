package org.usfirst.frc4048.utils;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * A wrapper class for the REV-11-1107 pressure sensor
 * The product datasheet can be here:
 * http://www.revrobotics.com/content/docs/REV-11-1107-DS.pdf
 */
public class PressureSensor {

    // The nominal input voltage to the sensor
    private static final double VCC = 5.0;

    private AnalogInput sensor;

    public PressureSensor(AnalogInput sensor) {
        assert(sensor != null);
        this.sensor = sensor;
    }

    public double getPressureInPSI() {
        return calcPressure(sensor.getVoltage());
    }

    private double calcPressure(double voltage) {
        return 250.0 * (voltage / VCC) - 25.0;
    }
}
