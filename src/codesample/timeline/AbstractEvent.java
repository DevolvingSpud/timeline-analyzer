package codesample.timeline;

public abstract class AbstractEvent implements Event {
	
	private long _start = 0;
	private long _end = 0;
	
	public AbstractEvent(long start) {
		this(start, start);
	}
	
	public AbstractEvent(long start, long end) {
		this._start = start;
		this._end = end;
	}

	public long getStart() {
		return _start;
	}

	public long getEnd() {
		return _end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (_end ^ (_end >>> 32));
		result = prime * result + (int) (_start ^ (_start >>> 32));
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
		if (_end != other._end)
			return false;
		if (_start != other._start)
			return false;
		return true;
	}

}
