package org.usfirst.frc4048.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.TimerTask;
import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Logging {

	public static enum MessageLevel {
		 INFORMATION
	}

	public static enum Subsystems {
		ELEVATOR, HATCHPANEL, CARGO, DRIVETRAIN, POWERDISTPANEL
	}

	private boolean writeLoggingGap = false;
	private static final int MSG_QUEUE_DEPTH = 512;
	private java.util.Timer executor;
	private long period;
	private WorkQueue wq;
	public static DecimalFormat df5 = new DecimalFormat(".#####");
	public static DecimalFormat df4 = new DecimalFormat(".####");
	public static DecimalFormat df3 = new DecimalFormat(".###");

	public Logging(long period, WorkQueue wq) {
		this.period = period;
		this.wq = wq;
	}
	
	abstract static public class LoggingContext {
		private int counter = 0;
		private final Subsystems subsystem;
		private final StringBuilder sb = new StringBuilder();
		private static final char COMMA = ',';
		private static final char QUOTE = '"';
		private boolean writeTitles = false;
		
		public LoggingContext(final Subsystems subsystem) {
			this.subsystem = subsystem;
		}
		
		abstract protected void addAll();
		
		final void writeHeadings() {
			writeTitles = true;
			writeData();
			writeTitles = false;
		}
		
		public final void writeData() {
			if ((DriverStation.getInstance().isEnabled() && (counter % 5 == 0)) || writeTitles) {
				sb.setLength(0);
				sb.append(df3.format(Timer.getFPGATimestamp()));
				sb.append(",");
				if(DriverStation.getInstance().isDisabled())
					sb.append(0);
				else
					sb.append(df3.format(Timer.getFPGATimestamp() - Robot.timeOfStart));
				sb.append(",");
				sb.append(subsystem.name());
				sb.append(",");
				addAll();
				Robot.logging.traceMessage(sb);
			}
		}
		
		protected void add(String title, int value) {
			if (writeTitles) {
				sb.append(QUOTE).append(title).append(QUOTE);
			}
			else {
				sb.append(Integer.toString(value));
			}
			sb.append(COMMA);
		}
		
		protected void add(String title, boolean value) {
			if (writeTitles) {
				sb.append(QUOTE).append(title).append(QUOTE);
			}
			else {
				sb.append(Boolean.toString(value));
			}
			sb.append(COMMA);
		}
		
		protected void add(String title, double value) {
			if (writeTitles) {
				sb.append(QUOTE).append(title).append(QUOTE);
			}
			else {
				sb.append(Double.toString(value));
			}
			sb.append(COMMA);
		}
		
		protected void add(String title, String value) {
			if (writeTitles) {
				sb.append(QUOTE).append(title).append(QUOTE);
			}
			else {
				sb.append(QUOTE).append(value).append(QUOTE);
			}
			sb.append(COMMA);
		}
	}

	public void startThread() {
		this.executor = new java.util.Timer();
		this.executor.schedule(new ConsolePrintTask(wq, this), 0L, this.period);
	}

	private void traceMessage(final StringBuilder sb) {
		if (writeLoggingGap) {
			if (wq.append("LOGGING GAP!!"))
				writeLoggingGap = false;
		}
		if (!wq.append(sb.toString()))
			writeLoggingGap = true;
	}

	public void traceMessage(MessageLevel ml, String ...vals) {
		final StringBuilder sb = new StringBuilder();
		sb.append(df3.format(Timer.getFPGATimestamp()));
		sb.append(",");
		if(DriverStation.getInstance().isDisabled())
			sb.append(0);
		else
			sb.append(df3.format(Timer.getFPGATimestamp() - Robot.timeOfStart));
		sb.append(",");
		sb.append(ml.name());
		sb.append(",");
		if (vals != null) {
			for (final String v : vals) {
				sb.append("\"").append(v).append("\"");
				sb.append(",");
			}
		}
		traceMessage(sb);
	}

	public void printHeadings() {
		final LoggingContext list[];

		// Might need to print these subsystem headings later
		// Robot.claw.loggingContext, Robot.wrist.loggingContext, Robot.intake.loggingContext,
		if(!RobotMap.ENABLE_DRIVETRAIN) {
			final LoggingContext temp[] = { Robot.drivetrain.loggingContext, Robot.pdp.loggingContext  };
			list = temp;
		} else {
			final LoggingContext temp[] = { Robot.pdp.loggingContext };
			list = temp;
		}
				
		for (final LoggingContext c : list) {
			c.writeHeadings();
		}
	}

	private class ConsolePrintTask extends TimerTask {
		PrintWriter log;
		final WorkQueue wq;
		final Logging l;

		private ConsolePrintTask(WorkQueue wq, Logging l) {
			this.l = l;
			this.wq = wq;
			log = null;
		}

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
						this.log = new PrintWriter("/home/lvuser/Logs/" + dateFormat.format(date) + "-Log.txt",
								"UTF-8");
					}

					log.flush();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				catch (Exception e){

					System.out.println(e);
				
				}
			}
			print();
		}
	}
}