package codesample.timeline;

import org.joda.time.DateTime;

public class NamedEvent extends AbstractEvent implements Event {
	
	private String _name;

	public NamedEvent(String name, DateTime start) {
		this(name, start,start);
	}

	public NamedEvent(String name, DateTime start, DateTime end) {
		super(start, end);
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
}
