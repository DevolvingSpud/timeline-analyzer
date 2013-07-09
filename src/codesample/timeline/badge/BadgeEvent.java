/**
 * 
 */
package codesample.timeline.badge;

import org.joda.time.DateTime;

import codesample.timeline.Event;

/**
 * An Event implementation that will handle badge events.
 * @author Lamont
 *
 */
public class BadgeEvent implements Event {
	
	/**
	 * An enumeration for the type of events that can occur.
	 * @author Lamont
	 *
	 */
	public enum Type { ADMITTED, REJECTED };
	
	// The timestamp of the badge entry.
	private DateTime timestamp = null;
	
	// The type of badge event that was recorded
	private BadgeEvent.Type eventType = Type.ADMITTED;
	
	// The name of the person that the event is for
	private String name = null;
	
	// The location the badge event occurred at
	private String location;
	
	/**
	 * A constructor for a BadgeEvent.
	 * @param name the name of the person being badged
	 * @param type the type of event 
	 * @param location the location they scanned ino
	 * @param timestamp the timestamp of the event
	 */
	public BadgeEvent(String name, BadgeEvent.Type type, String location, DateTime timestamp) {
		this.name = name;
		this.eventType = type;
		this.location = location;
		this.timestamp = timestamp;
	}

	/**
	 * @return the timestamp of the BadgeEvent
	 */
	public DateTime getStart() {
		return timestamp;
	}

	/**
	 * @return the timestamp of the event
	 */
	public DateTime getEnd() {
		return timestamp;
	}

	/**
	 * @return the eventType
	 */
	public BadgeEvent.Type getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(BadgeEvent.Type eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		BadgeEvent other = (BadgeEvent) obj;
		if (eventType != other.eventType)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BadgeEvent [").append(timestamp).append("]: ").append(name).append(" was ").append(eventType).append(" at ").append(location);
		return sb.toString();
	}
	


}
