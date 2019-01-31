package org.usfirst.frc4048.utils;

/**
 * Helper class to calculate the angle of the robot in relation to a flat
 * surface in front of two distance sensors.
 * 
 * <h1>Operating Principle</h1>
 * 
 * <pre>
 * 
 *                                    +-----------------------------+
 *                                    |ROBOT                        |
 *                                    |                             |
 *                                    |                             |
 *                                    |                             |
 *                                    |     S1                S2    |
 *                                    +-----+-----------------+-----+
 *                                          |                 |
 *                                          |                 |
 *                  o                       |                 |
 *                     o                    |D1               |D2
 *                 WALL   o                 |                 |
 *                           o              |                 |
 *                              o           |                 |
 *                                 o        |                 |
 *                                    o     |                 |
 *                                       o  |                 |
 *                                          +                 |
 *                                          :  o              |
 *                                          :     o           |
 *                                          :        o        |
 *                                 OPPOSITE :           o     |
 *                                          :      ANGLE   o  |
 *                                          + - - - - - - - - +
 *                                              ADJACENT         o
 *                                                                   o
 *                                                                      o
 * </pre>
 * 
 * <ul>
 * 
 * <li>D1 and D2 are the distance values read from each of the distance sensors
 * S1 and S2. They measure the distance from robot face to wall.</li>
 * 
 * <li>We can imagine a right triangle as shown on the diagram, where: 1) the
 * OPPOSITE side is the difference between D1 and D2 and 2) the ADJACENT side is
 * the distance between the sensors S1 and S2</li>
 * 
 * <li>Given the length of the 2 sides of this imaginary right triangle we can
 * calculate ANGLE.</li>
 * 
 * <li>ANGLE is same angle that the ROBOT must rotate so that it's face is
 * parallel to the WALL.</li>
 * 
 * </ul>
 * 
 * 
 * @see test.TestAngleFinder test cases
 */

public class AngleFinder {
  /**
   * Distance (in inches) between the two range finder. This is the adjacent side
   * of the triangle.
   */
  private final double distanceInInches;
  private final IRangeFinder rangeFinderL;
  private final IRangeFinder rangeFinderR;

  /**
   * Initialize the AngleFinder instance by giving it two IRangeFinder instances
   * and the distance between the two sensors in inches as they are mounted on the
   * robot.
   * 
   * @param rangeFinderL
   * @param rangeFinderR
   * @param distanceInInches
   */
  public AngleFinder(final IRangeFinder rangeFinderL, final IRangeFinder rangeFinderR, final double distanceInInches) {
    this.distanceInInches = distanceInInches;
    this.rangeFinderL = rangeFinderL;
    this.rangeFinderR = rangeFinderR;
  }

  /**
   * Read current distance values from the two range finders and calculate the
   * angle offset of the robot so the robot can turn parallel to the opposing
   * wall.
   * 
   * @return <b>0.0</b> when the robot side with the sensors is parallel to the
   *         opposing wall. <b><0.0</b> when the robot needs to rotate
   *         counterclockwise (topview). <b>>0.0</b> when the robot needs to
   *         rotate clockwise (topview).
   */
  public double calcAngleInDegrees() {
    final double leftInches = rangeFinderL.getDistanceInInches();
    final double rightInches = rangeFinderR.getDistanceInInches();

    final double opposite = leftInches - rightInches;
    final double angle = Math.toDegrees(Math.atan(distanceInInches / opposite));
    if (angle < 0)
      return -(90 + angle);
    else
      return 90 - angle;
  }

}