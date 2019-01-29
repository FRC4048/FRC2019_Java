package org.usfirst.frc4048.commands;

import java.util.Set;
import java.util.TreeSet;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.utils.Logging.MessageLevel;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

abstract public class LoggedCommand extends Command {
	private final String ident;
	private final Set<String> requirements = new TreeSet<String>();

	public LoggedCommand(final String ident) {
		this.ident = ident;
	}

	private void log(final String text) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append(" ");
		sb.append(ident);
		Robot.logging.traceMessage(MessageLevel.INFORMATION, sb.toString(), requirements.toString(), text);
	}
	
	/**
	 * Overrides Command.requires() so that LoggedCommand can log which subsystems
	 * are required by the command.
	 */
	@Override
	public synchronized void requires(Subsystem s) {
		super.requires(s);
		requirements.add(s.toString());
	}

	/**
	 * Overrides Command.clearRequirements() so that LoggedCommand can log which
	 * subsystems are required by the command.
	 */
	@Override
	public synchronized void clearRequirements() {
		super.clearRequirements();
		requirements.clear();
	}

	@Override
	final protected boolean isFinished() {
		final boolean result = loggedIsFinished();
		if (result)
			log(String.format("isFinished()"));
		return result;
	}

	abstract protected boolean loggedIsFinished();

	@Override
	final protected void initialize() {
		log("initialize()");
		loggedInitialize();
	}

	abstract protected void loggedInitialize();

	@Override
	final protected void execute() {
		//log("execute()");
		loggedExecute();
	}

	abstract protected void loggedExecute();

	@Override
	final protected void end() {
		log("end()");
		loggedEnd();
	}

	abstract protected void loggedEnd();

	@Override
	final protected void interrupted() {
		log("interrupted()");
		super.interrupted();
		loggedInterrupted();
	}

	/**
	 * Called from Command.interrupted()
	 */
	abstract protected void loggedInterrupted();

	@Override
	final protected synchronized boolean isTimedOut() {
		final boolean result = super.isTimedOut();
		if (result)
			log(String.format("isTimedOut()"));
		return result;
	}

	@Override
	final public synchronized void cancel() {
		super.cancel();
		loggedCancel();
		if (DriverStation.getInstance().isEnabled()) {
			log("cancel()");
		}
	}

	/**
	 * Called from Command.cancel()
	 */
	abstract protected void loggedCancel();
}