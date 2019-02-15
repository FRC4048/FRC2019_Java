package org.usfirst.frc4048;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * Change the interface definition as needed to use one of the specific hardware
 * maps.
 */

// public interface RobotMap extends RobotMapForTestbed {
public interface RobotMap extends RobotMapFor2018Robot {
  /**
  * Logger will log every number of iteations specified here
  */
  public static final int LOGGING_FREQ = 5;

  // Add constants that are not specific to the Robot hardware or behavior.
  // Use one of the RobotMapForXXXXX classes to add
  // definitions for the specific hardware.

  /**
   * Rough interval between reading the pigeon data on the pigeon thread. 11ms because it will be read
   * once per robot period loop.
   */
  public static final long PIGEON_READ_DELAY_MS = 11;

  public static final int SHOW_PIGEON_STATUS_SECONDS = 15;

  /**
   * Enable reading of the steer encoder position on a separate thread. This
   * should reduce the period cycle time.
   */
  public static final boolean ENABLE_WHEEL_ENCODER_THREAD = true;

  public static final boolean ENABLE_PIVOT = false;
  
  public static final long WHEEL_ENCODER_THREAD_INTERVAL_MS = 11;

}
