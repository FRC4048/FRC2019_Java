package org.usfirst.frc4048.utils;

/**
 * Utility class for capturing completed events during the robot execution and
 * the amount of time for each step.
 * 
 * The timer starts by a call to {@link #init(String)} and stops with a call to
 * term(). There is a finite number of recorded events possible and if this
 * limit is exceeded timing data will be silently lost.
 */
public class Timer {
  /**
   * Flag to indicate that timing data is being recorded.
   */
  private boolean enabled;

  /**
   * The next point in the data arrays that will be recorded. The {@link #time}
   * {@link #info} and {@link #caller} use this as the current array index that
   * will be written or the last array index that was written.
   */
  private int last = 0;

  /**
   * A series of time periods recorded for each event completed.
   */
  private final long time[];

  /**
   * A short identifier for each of the events recorded. There is nothing special
   * about these identifiers, but it would help if they were descriptive, short
   * and unique.
   */
  private final String info[];

  /**
   * A series of class names that recorded the completed events.
   */
  private final String caller[];
  private final int max;

  public Timer(final int max) {
    this.enabled = false;
    this.last = 0;
    this.max = max - 1;
    this.time = new long[max];
    this.info = new String[max];
    this.caller = new String[max];
  }

  /**
   * @return returns the total time (in milliseconds) between the start() and the
   *         last completed() event recorded.
   */
  public long total() {
    if (enabled)
      return time[last] - time[0];
    else
      return 0;
  }

  /**
   * Records a completed event to this timer. Ignored if the timer has not been
   * started.
   * 
   * @param caller this pointer of the object that recorded the event.
   * @param info   short identifier for the event completed.
   */
  public void completed(final Object caller, final String info) {
    if (enabled) {
      if (last < max) {
        last += 1;
        this.time[last] = System.currentTimeMillis();
        this.caller[last] = caller.getClass().getSimpleName();
        this.info[last] = info;
      }
    }
  }

  /**
   * Starts the timer. If the timer was already started it will reset it.
   * 
   * @param message short identifier for the timing data being collected.
   */
  public void init(final String message) {
    enabled = true;
    last = 0;
    time[last] = System.currentTimeMillis();
    info[last] = message;
  }

  /**
   * Ends the timer.
   */
  public void term() {
    enabled = false;
    last = 0;
  }

  /**
   * Returns a string of all the events completed in the order recorded and the
   * time difference (in milliseconds) between each event. Returns an empty string
   * if there was no timer started.
   */
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    if (enabled) {
      sb.append(info[0]).append(": ").append(total());
      String lastCaller = "";
      for (int i = 1; i <= last; i++) {
        if (!lastCaller.equals(caller[i])) {
          sb.append(" [").append(caller[i]).append("]");
          lastCaller = caller[i];
        }
        sb.append(" ").append(info[i]).append("+").append(time[i] - time[i - 1]);
      }
    }
    return sb.toString();
  }
}
