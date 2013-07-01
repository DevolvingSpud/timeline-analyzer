package codesample.timeline;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.DateTime;

public class Timeline implements Collection<Event> {
	
	private TreeMap<DateTime, Event> eventMap = new TreeMap<DateTime, Event>();
	
	@Override
	public boolean add(Event e) {
		if (e != null)
		{
			eventMap.put(e.getStart(), e);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Event> c) {
		return eventMap.values().addAll(c);
	}

	@Override
	public void clear() {
		eventMap.clear();
	}

	@Override
	public boolean contains(Object o) {
		return eventMap.containsValue(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return eventMap.values().containsAll(c);
	}

	@Override
	public boolean isEmpty() throws NullPointerException{
		return eventMap.isEmpty();
	}

	@Override
	public Iterator<Event> iterator() {
		return eventMap.values().iterator();
	}

	@Override
	public boolean remove(Object o) {
		return eventMap.remove(o) != null;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return eventMap.values().removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return eventMap.values().retainAll(c);
	}

	@Override
	public int size() {
		return eventMap.size();
	}

	@Override
	public Object[] toArray() {
		return eventMap.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return eventMap.values().toArray(a);
	}
	
//	public Event startedDuring(DateTime timeStart, DateTime timeEnd){
//		return 
//	}
}
