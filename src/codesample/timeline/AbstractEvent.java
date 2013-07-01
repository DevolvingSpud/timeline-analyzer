package codesample.timeline; 

import org.joda.time.DateTime;
import org.joda.time.Interval;

public abstract class AbstractEvent implements Event {
	
	private Interval _interval = null;
	
	public AbstractEvent(long start) {
		this(start, start);
	}
	
	public AbstractEvent(long start, long end) {
		_interval = new Interval(start, end);
	}

	public AbstractEvent(DateTime start) {
		this(start, start);
	}
	
	public AbstractEvent(DateTime start, DateTime end) {
		_interval = new Interval(start, end);
	}

	public DateTime getStart() {
		return _interval.getStart();
	}

	public DateTime getEnd() {
		return _interval.getEnd();
	}
	

	@Override
	public String toString() {
		return _interval.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_interval == null) ? 0 : _interval.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEvent other = (AbstractEvent) obj;
		if (_interval == null) {
			if (other._interval != null)
				return false;
		} else if (!_interval.equals(other._interval))
			return false;
		return true;
	}
	
}
