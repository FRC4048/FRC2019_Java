package org.usfirst.frc4048.utils;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * A wrapper class for the Sharp Optical Range Finder
 */
public class OpticalRangeFinder implements IRangeFinder {
    private final double COEFF_A = 1817.29393232899;
    private final double COEFF_B = 1.17165226621558;
    private final double COEFF_C = 0.0291621813457088;
    private final double COEFF_D = -0.807760211107265;

    private final double CM_2_INCH = 1/2.54;

    private AnalogInput sensor;

    public OpticalRangeFinder(AnalogInput sensor) {
        assert(sensor != null);
        this.sensor = sensor;
    }

    /**
     * @return the distance in Inches reads from the sensor. 0 in case we have no reading.
     */
    public double getDistance() {
        return calcDistance(sensor.getAverageVoltage()) * CM_2_INCH;
    }

    /**
     * This is the result of using curve-fitting algorithm to the values from the product data sheet
     * http://www.sharp-world.com/products/device/lineup/data/pdf/datasheet/gp2y0a21yk_e.pdf
     * https://mycurvefit.com/
     * Voltage  Distance
     *  0.4      80
     *  0.6      50
     *  0.75     40
     *  0.9      30
     *  1.1      25
     *  1.3      20
     *  1.7      15
     *  2.3      10
     *  2.75     8
     *  3        7
     *
     * @param value the average voltage value over multiple samples
     * @return the distance in Centimeters
     */
    private double calcDistance(double value) {
        return COEFF_D + ((COEFF_A - COEFF_D) / (1 + Math.pow((value/COEFF_C), COEFF_B)));
    }
}
