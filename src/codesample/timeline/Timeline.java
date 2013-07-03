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
	
	private TreeMap<DateTime, HashSet<Event>> eventMap = new TreeMap<DateTime, HashSet<Event>>();
	
	@Override
	public boolean add(Event e) {
		if (e != null)
		{
			if(!eventMap.containsKey(e.getStart()))   // If the DateTime is not there create a new key and hash set
			{
				eventMap.put(e.getStart(), new HashSet<Event>()); // Places the DateTime and an empty Set into the map
			}
				eventMap.get(e.getStart()).add(e); // Adds the new event to the empty Set in the given DateTime
				return true;
		}
			return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Timeline has ").append(eventMap.keySet().size()).append(" time keys");
		for (DateTime key : eventMap.keySet()) {
			HashSet<Event> value = eventMap.get(key);
			sb.append("\n ").append(key).append(" has ").append(value.size()).append(" events: ");
			for (Event e : value) {
				sb.append("\t").append(e);
			}
		}
		return sb.toString();
	}

	@Override
	public boolean addAll(Collection<? extends Event> c) {
		
		if (c !=null)
		{
			Iterator<? extends Event> eventIter = c.iterator();
			while (eventIter.hasNext())
				this.add(eventIter.next());
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		eventMap.clear(); // Clears the map
	}

	@Override
	public boolean contains(Object o) {
		
		if (o==null || !(o instanceof Event))
		{
			return false;
		}
		
		Event myEvent = (Event)o;
		DateTime startTime = myEvent.getStart();
		
		if (eventMap.containsKey(startTime)) //If there is a Key there must be an event, therefore if key = contains.
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		
		if (c !=null)
		{
			Iterator<?> eventIter = c.iterator();
			while (eventIter.hasNext())
			{
				this.contains(eventIter.next());
			}
		}
		return false;
	}

	@Override
	public boolean isEmpty(){
		if (eventMap.isEmpty()) // returns true if the map is empty
		{
			return true;
		}
		return false;
	}

	@Override
	public Iterator<Event> iterator() {
		return iterator();
	}

	@Override
	public boolean remove(Object o) {
		if (o==null || !(o instanceof Event)) //if the object is null or not event return false, cannot remove
		{
			return false;
		}
		
		Event myEvent = (Event)o;
		DateTime startTime = myEvent.getStart();
		
		if (!eventMap.containsKey(startTime)) // if the eventMap does not contain the key return false, cannot remove
		{
			return false;
		}
		
		if (eventMap.get(myEvent.getStart()).size()==1) // If the size of the set contains one event, remove the key
		{
			eventMap.remove(myEvent.getStart());
			return true;
			// remove key
		}
				
		HashSet<Event> events = eventMap.get(startTime); // else, remove the event located at a given key
		return events.remove(myEvent);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (c !=null)
		{
			Iterator<?> eventIter = c.iterator(); // iterate through the collection
			while (eventIter.hasNext())           // remove all instances in eventMap from collection
				this.remove(eventIter.next());
			return true;
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (c !=null)
		{
			Iterator<?> eventIter = c.iterator(); // iterate through the collection
			while (eventIter.hasNext())           // remove all instances in eventMap from collection
				this.remove(eventIter.next());
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return eventMap.size(); // returns the size of the evenMap
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
