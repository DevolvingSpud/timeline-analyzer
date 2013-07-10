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
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import codesample.timeline.Event;

/**
 * This class represents a daily badge record that consists of a list of
 * BadgeEvent instances.
 * 
 * @author Lamont
 * 
 */
public abstract class BadgeEntryExitRecord implements Event,
		Comparable<BadgeEntryExitRecord> {

	// The day that this event represents
	private LocalDate localDate = null;

	// The entire list of events for this date
	private List<BadgeEvent> badgeEvents;

	/**
	 * Constructor that builds its record based on the events that it has been
	 * given. It presumes that the first event on the Iterator is the entry
	 * event and it searches for an event that is an Exit event. The Iterator
	 * will be setup such that calling Iterator.next() will produce a next Entry
	 * event.
	 * 
	 * @param events
	 *            the iterator of events.
	 */
	public BadgeEntryExitRecord(ListIterator<BadgeEvent> events) {
		badgeEvents = new LinkedList<BadgeEvent>();
		while (events.hasNext()) {
			BadgeEvent daEvent = events.next();

			// If we don't have a LocalDate yet, this is the first event that
			// will produce one
			if (this.localDate == null) {
				this.localDate = daEvent.getStart().toLocalDate();
			}

			// If this event matches the date, add it to the list.
			// (SN: I know this is redundant, but I keep going back and forth
			// about passing in the LocalDate for this event.)
			if (this.localDate.equals(daEvent.getStart().toLocalDate())) {
				this.badgeEvents.add(daEvent);
				if (!isExit(daEvent)) {
					continue;
				}
			}
			// Invariant: It's not in the list...so break!
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see codesample.timeline.Event#getStart()
	 */
	public DateTime getStart() {
		if (badgeEvents.isEmpty()) {
			return localDate.toDateTime(new LocalTime(0, 0, 0));
		}
		Collections.sort(badgeEvents);
		return badgeEvents.get(0).getStart();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see codesample.timeline.Event#getEnd()
	 */
	public DateTime getEnd() {
		if (badgeEvents.isEmpty()) {
			return localDate.toDateTime(new LocalTime(23, 59, 59));
		}
		Collections.sort(badgeEvents);
		return badgeEvents.get(badgeEvents.size() - 1).getEnd();
	}

	/**
	 * Abstract method to determine if a particular BadgeEvent is an entry event
	 * or not.
	 * 
	 * @param event
	 *            the event to calculate
	 * @return true if this is an entry event and false otherwise
	 */
	protected abstract boolean isEntry(BadgeEvent event);

	/**
	 * Abstract method to determine if a particular BadgeEvent is an exit event
	 * or not.
	 * 
	 * @param event
	 *            the event to calculate
	 * @return true if this is an exit event and false otherwise
	 */
	protected abstract boolean isExit(BadgeEvent event);

	/**
	 * @return the localDate
	 */
	public LocalDate getLocalDate() {
		return localDate;
	}

	/**
	 * @return the badgeEvents
	 */
	public List<BadgeEvent> getBadgeEvents() {
		return badgeEvents;
	}
	
	/**
	 * 
	 * @return the period of time that was spent in the facility
	 */
	public Duration getElapsedTime() {
		return new Duration(this.getStart(), this.getEnd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((localDate == null) ? 0 : localDate.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BadgeEntryExitRecord other = (BadgeEntryExitRecord) obj;
		if (localDate == null) {
			if (other.localDate != null)
				return false;
		} else if (!localDate.equals(other.localDate))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BadgeEntryExitRecord[").append(localDate).append(" has ")
				.append(badgeEvents.size()).append(" events:\n");
		Collections.sort(badgeEvents);
		for (BadgeEvent event : badgeEvents) {
			sb.append("\t").append(event).append("\n");
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(BadgeEntryExitRecord o) {
		// We'll compare the start times for the BadgeEvents
		DateTime myStart = this.getStart();
		return (myStart == null) ? -1 : myStart.compareTo(o.getStart());
	}

}
