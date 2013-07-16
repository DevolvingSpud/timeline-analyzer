package codesample.timeline;

import org.joda.time.DateTime;

public class NamedEvent extends AbstractEvent implements Event, Comparable<Event> {
	
	private String _name;

	public NamedEvent(String name, DateTime start) {
		this(name, start, start);
	}

	public NamedEvent(String name, DateTime start, DateTime end) {
		super(start, end);
		_name = name;
	}
	
	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamedEvent other = (NamedEvent) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event Name: " + _name + "]";
	}

	@Override
	public int compareTo(Event otherEvent) {
		int startCompare = this.getStart().compareTo(otherEvent.getStart());
		int endCompare = this.getEnd().compareTo(otherEvent.getEnd());
		if (startCompare != 0) return startCompare;
		else if (endCompare != 0) return endCompare;
		else if (otherEvent instanceof NamedEvent) {
			return this.getName().compareTo(((NamedEvent)otherEvent).getName());
		}
		else return 0;
	}	
	
}
