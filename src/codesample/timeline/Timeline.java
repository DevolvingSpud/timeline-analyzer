package codesample.timeline;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.joda.time.DateTime;

public class Timeline implements Collection<Event> {
	
	private TreeMap<DateTime, HashSet<Event>> eventMap = new TreeMap<DateTime, HashSet<Event>>();
	
	@Override
	public boolean add(Event e) {
		return addToEventMap(eventMap, e);
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
		
		if (eventMap.containsKey(startTime)) // Check to see if the key is there.
		{
			// Then check if the event is there
			if (eventMap.get(startTime).contains(myEvent))
			{
				return true;
			}
			else
				return false;
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
				if(!this.contains(eventIter.next()))
				{
					return false;
				}
			}
			return true;
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
		
		Iterator<Entry<DateTime, HashSet<Event>>> eventIter = eventMap.entrySet().iterator();
		
		while(eventIter.hasNext())
		{
			eventMap.Entry pairs = (eventMap)eventIter.next();
		}
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
//			for (Object next:c) // remove all instances in eventMap from collection
//			{
//				if (!c.contains(next))
//				{
//					this.remove(next);
//				}
//			}
//			return true;
			
//			HashSet<Event> set = new HashSet<Event>();
			
			TreeMap<DateTime, HashSet<Event>> newEventMap = new TreeMap<DateTime, HashSet<Event>>();

			for (Object e: c)
			{
				if (this.contains(e) && e instanceof Event)
				{
					addToEventMap(newEventMap, (Event)e);
				}
			}
			
			if (eventMap.size() == newEventMap.size())
			{
				return false;
			}
			
			eventMap = newEventMap;
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
	
	private boolean addToEventMap(TreeMap<DateTime, HashSet<Event>> set, Event e) {
		if (e != null)
		{
			if(!set.containsKey(e.getStart()))   // If the DateTime is not there create a new key and hash set
			{
				set.put(e.getStart(), new HashSet<Event>()); // Places the DateTime and an empty Set into the map
			}
				set.get(e.getStart()).add(e); // Adds the new event to the empty Set in the given DateTime
				return true;
		}
			return false;
	}
	
//	public Event startedDuring(DateTime timeStart, DateTime timeEnd){
//		return 
//	}
}
