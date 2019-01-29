package org.usfirst.frc4048.utils;

/**
 * Helper class to calculate the angle of the robot in relation to a flat
 * surface in front of two distance sensors.
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
	public AngleFinder(final IRangeFinder rangeFinderL, final IRangeFinder rangeFinderR,
			final double distanceInInches) {
		this.distanceInInches = distanceInInches;
		this.rangeFinderL = rangeFinderL;
		this.rangeFinderR = rangeFinderR;
	}

	/**
	 * Read current distance values from the two range finders and calculate the
	 * angle offset of the robot so the robot can turn parallel to the opposing
	 * wall.
	 */
	public double calcAngleInDegrees() {
		final double leftInches = rangeFinderL.getDistance();
		final double rightInches = rangeFinderR.getDistance();

		final double opposite = leftInches - rightInches;
		final double angle = Math.toDegrees(Math.atan(distanceInInches / opposite));
		return angle;
	}

}