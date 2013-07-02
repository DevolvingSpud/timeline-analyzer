package codesample.timeline;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.joda.time.DateTime;

public class Timeline implements Collection<Event> {
	
	
	private HashSet<Event> eventSet = new HashSet<Event>();
	private TreeMap<DateTime, HashSet<Event>> eventMap = new TreeMap<DateTime, HashSet<Event>>();
	
	
	@Override
	public boolean add(Event e) {
		if (e != null)
		{
			if(!eventMap.containsKey(e.getStart()))   // If the DateTime is not there create a new key and hash set
			{
				eventMap.put(e.getStart(), eventSet); // Places the DateTime and an empty Set into the map
				eventSet.add(e);	 				  // Adds the new event to the empty Set in the given DateTime
				return true;
			}
			else 									  // If the Key already exists Add to the existing set
			{
				eventSet.add(e);
				return true;
			}
		}
			return false;
	}

	@Override
	public boolean addAll(Collection<? extends Event> c) {
		
		if (c != null)
		{
//			eventSet.addAll(c);
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		eventMap.clear(); // Clears the map
		eventSet.clear(); // Clears remaining sets
	}

	@Override
	public boolean contains(Object o) {
		if (!(o instanceof Event))
		{
			return false;
		}
		
		Event myEvent = (Event)o;
		DateTime startTime = myEvent.getStart();
		
		if (eventSet.contains(myEvent) || eventMap.containsKey(startTime))
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if(eventSet.containsAll(c))
		{
			eventSet.containsAll(c);
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty(){
		if (eventMap.isEmpty() && eventSet.isEmpty()) // Makes sure both the map AND set are empty
		{
			return true;
		}
		return false;
	}

	@Override
	public Iterator<Event> iterator() {
		return null;
	}

	@Override
	public boolean remove(Object o) {
		if (o==null || !(o instanceof Event))
		{
			return false;
		}
		Event myEvent = (Event)o;
		DateTime startTime = myEvent.getStart();
		
		if (!eventMap.containsKey(startTime))
		{
			return false;
		}
		
		HashSet<Event> events = eventMap.get(startTime);
		return events.remove(myEvent);
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
		return eventMap.size()+1;
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
