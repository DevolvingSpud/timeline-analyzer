/**
 * 
 */
package codesample.timeline.badge;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.Seconds;

/**
 * A report generator for the DailyBadgeEvent stuff.
 * 
 * @author Lamont
 * 
 */
public class ReportGenerator {
	
	/** The number of seconds in 10 minutes. */
	private static Seconds TEN_MINUTES = Minutes.minutes(10).toStandardSeconds();

	/**
	 * Generates the DailyStatsReport given a list of DailyBadgeRecords and an
	 * output writer.
	 * 
	 * @param records
	 *            the records to output
	 * @param out the output writer
	 * @throws IOException if there was a problem writing to the output stream
	 */
	public static void generateDailyStatsReport(List<DailyBadgeRecord> records,
			PrintWriter out) throws IOException {
		
		// Generate the header
		out.println("Date\tFirst Time In\tLast Time Out\tElapsed Time\tCovered Time\tGap Time\tNum Intervals\n");

		// Generate the output report for each record
		Collections.sort(records);
		for (DailyBadgeRecord rec : records) {
			out.printf("%s\t", rec.getDate().toString());
			out.printf("%s\t", rec.getStart().toString());
			out.printf("%s\t", rec.getEnd().toString());
			Period elapsed = new Period(rec.getStart(), rec.getEnd());
			out.printf("%02d:%02d:%02d\t", elapsed.getHours(), elapsed.getMinutes(), elapsed.getSeconds());
			Period covered = rec.getTimeInFacility().toPeriod();
			out.printf("%02d:%02d:%02d\t", covered.getHours(), covered.getMinutes(), covered.getSeconds());
			Period gaps = rec.getTotalGapDuration().toPeriod();
			out.printf("%02d:%02d:%02d\t", gaps.getHours(), gaps.getMinutes(), gaps.getSeconds());
			out.printf("%02d\t", rec.getIntervalsInFacility().size());
			out.println();
		}
	}
	
	public static void generateGapsReport(List<DailyBadgeRecord> records, PrintWriter out) throws IOException {
		// Generate the header
		out.println("Date\tEntry Time\tExit Time\tGap Time\t");
		
		// Generate the records as necessary
		Collections.sort(records);
		for (DailyBadgeRecord rec : records) {
			Duration gapDuration = rec.getTotalGapDuration();
			if (gapDuration.toStandardSeconds().isGreaterThan(ReportGenerator.TEN_MINUTES)) {
				// This is a gap that we care about
				Interval lastInterval = null;
				for (Interval gapInterval : rec.getIntervalsInFacility()) {
					out.printf("%s\t", rec.getDate().toString());
					out.printf("%s\t%s\t", gapInterval.getStart(), gapInterval.getEnd());
					if (lastInterval != null) {
						Period daGap = lastInterval.gap(gapInterval).toPeriod();
						out.printf("%02d:%02d:%02d\t", daGap.getHours(), daGap.getMinutes(), daGap.getSeconds());
					}
					lastInterval = gapInterval;
					out.println();
				}
			}
		}
	}

}
