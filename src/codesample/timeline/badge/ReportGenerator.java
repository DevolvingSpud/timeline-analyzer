/**
 * 
 */
package codesample.timeline.badge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

/**
 * A report generator for the DailyBadgeEvent stuff.
 * 
 * @author Lamont
 * 
 */
public class ReportGenerator {

	// The default delimiter to use for reports
	private static final String DEFAULT_DELIMITER = "\t";

	private String delimiter = null;

	public ReportGenerator(String delimiter) {
		this.delimiter = delimiter;
		if ((this.delimiter == null) || this.delimiter.isEmpty()) {
			this.delimiter = DEFAULT_DELIMITER;
		}
	}

	/**
	 * Helper method to output periods more uniformly.
	 * 
	 * @param daPeriod
	 *            The period to output
	 * @param out
	 *            The PrintWriter to output the period to
	 */
	private void outputPeriod(Period daPeriod, PrintWriter out) {
		out.printf("%s%s", daPeriod.toString(Format.PERIOD), this.delimiter);
		int seconds = daPeriod.toStandardSeconds().getSeconds();
		float hours = seconds / 3600f;
		out.printf("%2.2f%s", hours, this.delimiter);

	}

	/**
	 * Generates the DailyStatsReport given a list of DailyBadgeRecords and an
	 * output writer.
	 * 
	 * @param records
	 *            the records to output
	 * @param outFile
	 *            the output file
	 * @throws IOException
	 *             if there was a problem writing to the output file
	 */
	public void generateDailyStatsReport(List<DailyBadgeRecord> records,
			File outFile) throws IOException {

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				outFile)));

		// Generate the header
		out.printf(
				"Date%sFirst Time In%sLast Time Out%sElapsed Time (HMS)%sElapsed Time (Hours)%sCovered Time (HMS)%sCovered Time (Hours)%sGap Time (HMS)%sGap Time (Hours)%sNum Intervals",
				this.delimiter, this.delimiter, this.delimiter, this.delimiter,
				this.delimiter, this.delimiter, this.delimiter, this.delimiter,
				this.delimiter);
		out.println();

		// Generate the output report for each record
		Collections.sort(records);
		for (DailyBadgeRecord rec : records) {
			out.printf("%s%s%s%s%s%s", rec.getDate()
					.toString(Format.SHORT_DATE), this.delimiter, rec
					.getStart().toString(Format.DATE_TIME), this.delimiter, rec
					.getEnd().toString(Format.DATE_TIME), this.delimiter);

			Period elapsed = new Period(rec.getStart(), rec.getEnd());
			outputPeriod(elapsed, out);
			Period covered = rec.getTimeInFacility().toPeriod();
			outputPeriod(covered, out);
			Period gaps = rec.getTotalGapDuration().toPeriod();
			outputPeriod(gaps, out);
			out.printf("%02d%s", rec.getIntervalsInFacility().size(),
					this.delimiter);
			out.println();
		}

		out.flush();
		out.close();

		System.out.println("Wrote Daily Stats Report to "
				+ outFile.getAbsolutePath());
	}

	/**
	 * Generate a report of all of the gaps.
	 * 
	 * @param records
	 *            The list of records
	 * @param outFile
	 *            The output file
	 * @throws IOException
	 *             if there was a problem writing to the output file
	 */
	public void generateGapsReport(List<DailyBadgeRecord> records, File outFile)
			throws IOException {
		// Generate the header
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				outFile)));
		out.printf(
				"Date%sEntry Time%sExit Time%sGap Time (HMS)%sGap Time (Hours)",
				this.delimiter, this.delimiter, this.delimiter, this.delimiter);
		out.println();

		// Generate the records as necessary
		Collections.sort(records);
		for (DailyBadgeRecord rec : records) {
			Duration gapDuration = rec.getTotalGapDuration();
			// This is a gap that we care about
			Interval lastInterval = null;
			for (Interval gapInterval : rec.getIntervalsInFacility()) {
				out.printf("%s%s%s%s%s%s",
						rec.getDate().toString(Format.MID_DATE),
						this.delimiter,
						gapInterval.getStart().toString(Format.DATE_TIME),
						this.delimiter,
						gapInterval.getEnd().toString(Format.DATE_TIME),
						this.delimiter);
				if (lastInterval != null) {
					Period daGap = lastInterval.gap(gapInterval).toPeriod();
					outputPeriod(daGap, out);
				}
				lastInterval = gapInterval;
				out.println();
			}
		}

		out.flush();
		out.close();
		System.out.println("Wrote Gaps Report to " + outFile.getAbsolutePath());
	}

	/**
	 * Generates the list of BadgeEntryExitRecord instances.
	 * 
	 * @param records
	 *            The records to generate
	 * @param outFile
	 *            The output file
	 * @throws IOException
	 *             if there is an issue writing
	 */
	public void generateEntryExitRecordsReport(
			List<BadgeEntryExitRecord> records, File outFile)
			throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				outFile)));
		out.printf(
				"Date%sTime In%sTime Out%sElapsed Time (HMS)%sElapsedTime (hours)",
				this.delimiter, this.delimiter, this.delimiter, this.delimiter);
		out.println();

		Collections.sort(records);
		for (BadgeEntryExitRecord rec : records) {
			out.printf("%s%s%s%s%s%s",
					rec.getLocalDate().toString(Format.MID_DATE),
					this.delimiter,
					rec.getStart().toString(Format.TIME_OF_DAY),
					this.delimiter, rec.getEnd().toString(Format.TIME_OF_DAY),
					this.delimiter);
			outputPeriod(rec.getElapsedTime().toPeriod(), out);
			out.println();
		}

		out.flush();
		out.close();
		System.out.println("Wrote Entry Exit Records report to "
				+ outFile.getAbsolutePath());
	}

}
