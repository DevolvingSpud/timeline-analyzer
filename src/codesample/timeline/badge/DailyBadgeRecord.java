/**
 * 
 */
package codesample.timeline.badge;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import codesample.timeline.Event;

/**
 * A daily badge record.
 * 
 * @author Lamont
 * 
 */
public class DailyBadgeRecord implements Event, Comparable<Event> {

	// The badge date
	private LocalDate date;

	// The entry records
	private List<BadgeEntryExitRecord> entryRecords;

	/**
	 * Constructor.
	 * 
	 * @param date
	 *            the entry date
	 */
	public DailyBadgeRecord(LocalDate date) {
		this.date = date;
		entryRecords = new LinkedList<BadgeEntryExitRecord>();
	}

	public DailyBadgeRecord(BadgeEntryExitRecord firstRecord) {
		this.date = firstRecord.getLocalDate();
		entryRecords = new LinkedList<BadgeEntryExitRecord>();
		entryRecords.add(firstRecord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see codesample.timeline.Event#getStart()
	 */
	public DateTime getStart() {
		if ((entryRecords == null) || entryRecords.isEmpty()) {
			return null;
		}
		Collections.sort(entryRecords);
		return entryRecords.get(0).getStart();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see codesample.timeline.Event#getEnd()
	 */
	public DateTime getEnd() {
		if ((entryRecords == null) || entryRecords.isEmpty()) {
			return null;
		}
		Collections.sort(entryRecords);
		return entryRecords.get(entryRecords.size() - 1).getEnd();
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @return the entryRecords
	 */
	public List<BadgeEntryExitRecord> getEntryRecords() {
		return entryRecords;
	}

	/**
	 * @return a Duration object that represents the total time in the facility
	 */
	public Duration getTimeInFacility() {
		Duration coveredTime = new Duration(0);
		for (BadgeEntryExitRecord rec : entryRecords) {
			coveredTime = coveredTime.plus(rec.getElapsedTime());
		}
		return coveredTime;
	}

	/**
	 * Calculate the total duration of all of the gaps in these intervals.
	 * 
	 * @return the total duration of all gaps
	 */
	public Duration getTotalGapDuration() {
		Duration gaps = new Duration(0);
		List<Interval> intervals = getIntervalsInFacility();
		if (intervals.size() == 1) {
			return gaps;
		}
		Interval lastInterval = null;
		for (Interval thisInterval : intervals) {
			Interval gapInterval = lastInterval == null ? null : lastInterval
					.gap(thisInterval);
			if (gapInterval != null) {
				gaps = gaps.plus(gapInterval.toDuration());
			}
			lastInterval = thisInterval;
		}
		return gaps;
	}

	/**
	 * Gets the intervals that are within the facility. This is useful for
	 * determining both the start and stop times for intervals within the
	 * facility and for determining where the facility gaps are.
	 * 
	 * @return a list containing the intervals of time spent in the facility
	 */
	public List<Interval> getIntervalsInFacility() {
		List<Interval> intervals = new LinkedList<Interval>();
		Collections.sort(entryRecords);
		for (BadgeEntryExitRecord rec : entryRecords) {
			intervals.add(new Interval(rec.getStart(), rec.getEnd()));
		}
		return intervals;
	}

	/**
	 * Should we include this badge record in this daily record?
	 * 
	 * @param entryRecord
	 *            the badge record
	 * @return true if we should include it
	 */
	public boolean shouldInclude(BadgeEntryExitRecord entryRecord) {
		return date.equals(entryRecord.getLocalDate());
	}

	/**
	 * Add the entry record to this record.
	 * 
	 * @param entryRecord
	 *            the badge record
	 */
	public void addEntryRecord(BadgeEntryExitRecord entryRecord) {
		if (shouldInclude(entryRecord)) {
			entryRecords.add(entryRecord);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Event o) {
		DateTime myStart = this.getStart();
		DateTime otherStart = o == null ? null : o.getStart();
		if (myStart == null) {
			return -1;
		}
		return myStart.compareTo(otherStart);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DailyBadgeRecord for ")
				.append(date.toString(Format.SHORT_DATE)).append(":\n");
		sb.append("\tFirst Entry: ")
				.append(this.getStart().toString(Format.TIME_OF_DAY))
				.append("\n").append("\tLast Exit: ")
				.append(this.getEnd().toString(Format.TIME_OF_DAY))
				.append("\n");
		Duration elapsedTime = this.getTimeInFacility();
		Period elapsedPeriod = elapsedTime.toPeriod();
		sb.append("\tElapsed Time In Facility: ")
				.append(elapsedPeriod.toString(Format.PERIOD)).append("\n");
		sb.append("\tIntervals are as follows:\n");
		ListIterator<Interval> intervals = this.getIntervalsInFacility()
				.listIterator();
		while (intervals.hasNext()) {
			Interval lastInterval = intervals.hasPrevious() ? intervals
					.previous() : null;
			if (lastInterval != null) {
				intervals.next();
			}
			Interval thisInterval = intervals.next();
			Interval gapInterval = lastInterval == null ? null : lastInterval
					.gap(thisInterval);
			sb.append("\t\tEntry: ").append(thisInterval.getStart().toString(Format.TIME_OF_DAY)).append("\t")
					.append("Exit: ").append(thisInterval.getEnd().toString(Format.TIME_OF_DAY)).append("\t")
					.append("Elapsed: ").append(thisInterval.toPeriod().toString(Format.PERIOD));
			if (gapInterval != null) {
				Period gap = gapInterval.toPeriod();
				sb.append("\tGap: ").append(gap.toString(Format.PERIOD));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
