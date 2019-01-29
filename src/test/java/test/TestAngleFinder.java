package test;

import org.junit.Assert;
import org.junit.Test;
import org.usfirst.frc4048.utils.AngleFinder;
import org.usfirst.frc4048.utils.IRangeFinder;

/**
 * Used https://www.omnicalculator.com/math/right-triangle-side-angle to
 * validate angle calculations.
 */
public class TestAngleFinder {

  /**
   * Utility function to create a RangeFinder that returns a known constant
   * distance.
   */
  IRangeFinder dist(final double value) {
    return new IRangeFinder() {
      public double getDistanceInInches() {
        return value;
      }
    };
  }

  private static final double PRECISION = 0.01;

  /**
   * Common test function. Provide known left and right distances, perform the
   * angle calculation and assert that it was calculated to the expected value.
   * 
   * Uses PRECISION to determine equality.
   * 
   * @param distL         Constant left distance for test
   * @param distR         Constant right distance for test
   * @param expectedAngle expected result angle from {@link AngleFinder}
   */
  private void testCase(final double distL, final double distR, final double expectedAngle) {
    AngleFinder af = new AngleFinder(dist(distL), dist(distR), 10);
    Assert.assertEquals(expectedAngle, af.calcAngleInDegrees(), PRECISION);
  }

  @Test
  public void test10x10() {
    testCase(10, 10, 0);
  }

  @Test
  public void test11x10() {
    testCase(11, 10, 5.71);
  }

  @Test
  public void test12x10() {
    testCase(12, 10, 11.31);
  }

  @Test
  public void test13x10() {
    testCase(13, 10, 16.70);
  }

  @Test
  public void test14x10() {
    testCase(14, 10, 21.80);
  }

  @Test
  public void test15x10() {
    testCase(15, 10, 26.56);
  }

  @Test
  public void test16x10() {
    testCase(16, 10, 30.96);
  }

  @Test
  public void test17x10() {
    testCase(17, 10, 34.99);
  }

  @Test
  public void test18x10() {
    testCase(18, 10, 38.66);
  }

  @Test
  public void test19x10() {
    testCase(19, 10, 41.98);
  }

  @Test
  public void test20x10() {
    testCase(20, 10, 45.0);
  }

  @Test
  public void test0x10() {
    testCase(0, 10, -45.0);
  }

  @Test
  public void test10x0() {
    testCase(10, 0, 45.0);
  }
  
}
