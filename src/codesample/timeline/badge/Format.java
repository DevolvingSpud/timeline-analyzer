package codesample.timeline.badge;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * An interface to contain all of the formatters for consistency.
 * 
 * @author Lamont
 * 
 */
public interface Format {

	// Format for the full DateTime
	public DateTimeFormatter DATE_TIME = DateTimeFormat
			.forPattern("MM-dd-yyyy HH:mm:ss");

	// Format for a short date
	public DateTimeFormatter SHORT_DATE = DateTimeFormat
			.forPattern("MM-dd-yyyy");

	// Format for a mid-sized date
	public DateTimeFormatter MID_DATE = DateTimeFormat
			.forPattern("dd MMM yyyy");

	// Format for a time of day
	public DateTimeFormatter TIME_OF_DAY = DateTimeFormat
			.forPattern("HH:mm:ss");

	// Formatter for a period of time
	public PeriodFormatter PERIOD = new PeriodFormatterBuilder()
			.printZeroAlways().minimumPrintedDigits(2).appendHours()
			.appendSeparator(":").appendMinutes().appendSeparator(":")
			.appendSeconds().toFormatter();
}