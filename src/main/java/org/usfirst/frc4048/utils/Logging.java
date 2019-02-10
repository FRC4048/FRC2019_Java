package org.usfirst.frc4048.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Logging implements RobotMap {

  public static enum MessageLevel {
    INFORMATION, TIMER
  }

  private boolean writeLoggingGap = false;

  /**
   * Time period in milliseconds between writing to the log file by the dedicated
   * logging thread.
   */
  private static final int LOGGING_PERIOD = 100;

  /**
   * Maximum size of the work queue for sending messages to the dedicated logging
   * thread.
   */
  private static final int LOGGING_QUEUE_DEPTH = 512;
  
  private final WorkQueue wq;
  private int counter = 0;
  
  public final static DecimalFormat df5 = new DecimalFormat(".#####");
  public final static DecimalFormat df4 = new DecimalFormat(".####");
  public final static DecimalFormat df3 = new DecimalFormat(".###");
  private final static char COMMA = ',';
  private final static char QUOTE = '"';

  private final static ArrayList<LoggingContext> loggingContexts = new ArrayList<LoggingContext>();
  private static double initTime = 0;

  /**
   * Initialize logger with default settings.
   */
  public Logging() {
    this(LOGGING_PERIOD, new WorkQueue(LOGGING_QUEUE_DEPTH));
  }

  public Logging(long period, WorkQueue wq) {
    this.wq = wq;
    initTime = Timer.getFPGATimestamp();
    Robot.scheduleTask(new ConsolePrintTask(), period);
  }

  abstract static public class LoggingContext {
    private int counter = 0;
    private final String subsystem;
    private final StringBuilder sb = new StringBuilder();
    private boolean writeTitles = false;

    public LoggingContext(final String subsystem) {
      this.subsystem = subsystem;
      loggingContexts.add(this);
    }
    
    public LoggingContext(final Class<?> subsystem) {
      this(subsystem.getSimpleName());
    }

    abstract protected void addAll();

    private final void writeTitles() {
      writeTitles = true;
      writeData();
      writeTitles = false;
    }

    private final void writeData() {
      counter += 1;
      if ((DriverStation.getInstance().isEnabled() && (counter % RobotMap.LOGGING_FREQ == 0)) || writeTitles) {
        final double now = Timer.getFPGATimestamp();
        sb.setLength(0);
        sb.append(df3.format(now));
        sb.append(COMMA);
        if (DriverStation.getInstance().isDisabled())
          sb.append(0);
        else
          sb.append(df3.format(now - initTime));
        sb.append(COMMA);
        sb.append(subsystem);
        sb.append(COMMA);
        addAll();
        Robot.logging.traceMessage(sb);
      }
    }

    protected void add(String title, int value) {
      if (writeTitles) {
        sb.append(QUOTE).append(title).append(QUOTE);
      } else {
        sb.append(Integer.toString(value));
      }
      sb.append(COMMA);
    }

    protected void add(String title, boolean value) {
      if (writeTitles) {
        sb.append(QUOTE).append(title).append(QUOTE);
      } else {
        sb.append(Boolean.toString(value));
      }
      sb.append(COMMA);
    }

    protected void add(String title, double value) {
      if (writeTitles) {
        sb.append(QUOTE).append(title).append(QUOTE);
      } else {
        sb.append(Double.toString(value));
      }
      sb.append(COMMA);
    }

    protected void add(String title, String value) {
      if (writeTitles) {
        sb.append(QUOTE).append(title).append(QUOTE);
      } else {
        sb.append(QUOTE).append(value).append(QUOTE);
      }
      sb.append(COMMA);
    }
  }

  private void traceMessage(final StringBuilder sb) {
    if (writeLoggingGap) {
      if (wq.append("LOGGING GAP!!"))
        writeLoggingGap = false;
    }
    if (!wq.append(sb.toString()))
      writeLoggingGap = true;
  }

  public void traceMessage(MessageLevel ml, String... vals) {
    final double now = Timer.getFPGATimestamp();
    final StringBuilder sb = new StringBuilder();
    sb.append(df3.format(now));
    sb.append(COMMA);
    if (DriverStation.getInstance().isDisabled())
      sb.append(0);
    else
      sb.append(df3.format(now - initTime));
    sb.append(COMMA);
    sb.append(ml.name());
    sb.append(COMMA);
    if (vals != null) {
      for (final String v : vals) {
        sb.append(QUOTE).append(v).append(QUOTE);
        sb.append(COMMA);
      }
    }
    traceMessage(sb);
  }
  
  /**
   * Iterate through the known logging contexts and write the data for each of
   * them. Logs one logging context every time it'called. It's called by the
   * period() method and we want to spread the cost of logging over multiple calls
   * so we don't run over the 20ms budget.
   */
  public void writeAllData() {
    final int idx = (counter++) % loggingContexts.size();
    final LoggingContext lc = loggingContexts.get(idx);
    lc.writeData();
  }

  /**
   * Iterate through all known logging contexts and write the title for each of
   * them. The #writeAllData and #writeAllTitles functions must iterate through
   * the contexts in the same order so the titles and data are corresponding.
   */
  public void writeAllTitles() {
    for (final LoggingContext lc : loggingContexts) {
      lc.writeTitles();
    }
  }

  private class ConsolePrintTask implements Runnable {
    private PrintWriter log = null;

    public void print() {
      // Log all events, we want this done also when the robot is disabled
      for (;;) {
        final String message = wq.getNext();
        if (message != null)
          log.println(message);
        else
          break;
      }
      log.flush();
    }

    /**
     * Called periodically in its own thread
     */
    public void run() {
      if (log == null) {
        try {
          File file = new File("/home/lvuser/Logs");
          if (!file.exists()) {
            if (file.mkdir()) {
              System.out.println("Log Directory is created!");
            } else {
              System.out.println("Failed to create Log directory!");
            }
          }
          Date date = new Date();
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_ss-SSS");
          dateFormat.setTimeZone(TimeZone.getTimeZone("EST5EDT"));
          try {
            this.log = new PrintWriter("/media/sda1/" + dateFormat.format(date) + "-Log.csv", "UTF-8");
          } catch (Exception e) {
            this.log = new PrintWriter("/home/lvuser/Logs/" + dateFormat.format(date) + "-Log.txt", "UTF-8");
          }

          log.flush();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }

        catch (Exception e) {

          System.out.println(e);

        }
      }
      print();
    }
  }
}