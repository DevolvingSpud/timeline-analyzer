package codesample.timeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
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
		return new TimelineIterator();
		
	}
	
	/**
     * This Iterator is designed to walk through the Timeline as if it were an
     * asymmetrical 2-dimensional array. To do this, we're only keeping track of
     * indexKey and indexValue and doing the navigation through the underlying
     * eventMap to determine what will actually work.
     *
     * 
     *
     */
    private class TimelineIterator implements ListIterator<Event>

    {
        private int indexKey = 0;
        private int indexValue = 0;
        Object[] values = new Object[0];
        
        protected TimelineIterator()
        {
            if (eventMap != null && !eventMap.isEmpty())
            {
                Collection<HashSet<Event>> valueCollection = eventMap.values();
                if (valueCollection != null)
                {
                    values = valueCollection.toArray();
                }
            }            
        }

        @Override
        public boolean hasNext()
        {
                // Case #1: [on current key, there is a value next] => return true.
                if ((indexKey < values.length) && values.length == indexValue+1)
                {
                    
                    return true;
                }
                
                // Case #2: [on current key, there is not a value next]. Go to next key: there is not another key => false.
                if (indexKey+1 < values.length && indexValue < )
                {
                    
                    return false;
                }
                
                // Case #3: [on current key, there is not a value next]. Go to next key: there is another key ==> true.
                if ()
                {
                    
                    return true;
                }
        }

        @Override
        public Event next()
        {
            
            return null;
        }

        @Override
        public boolean hasPrevious()
        {
            return false;
        }

        @Override
        public Event previous()
        {
            return null;
        }

        @Override
        public int nextIndex()
        {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove()
        {

        }

        // not a necessary method for our case.
        @Override
        public void set(Event e)
        {
            throw new UnsupportedOperationException(
                    "We're not implementing set!");
        }

        @Override
        public void add(Event e)
        {

        }
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
		
		if(c!=null)
		{
			TreeMap<DateTime, HashSet<Event>> newEventMap = new TreeMap<DateTime, HashSet<Event>>();
			
			for (Object e: c)
			{
				if (e instanceof Event && this.contains(e))
				{
					addToEventMap(newEventMap, (Event)e);
				}
			}
			
//			System.out.println("EventMap size is: "+this.size());
//			System.out.println("newEventMap size is: "+newEventMap.size());
			
			if (sizeEventMap(eventMap) == sizeEventMap(newEventMap))
			{
				return false;
			}
			
			eventMap= newEventMap;
			return true;
		}		
	return false;
		
	}

	@Override
	public int size() {	
		return sizeEventMap(eventMap);
	}

	@Override
	public Object[] toArray() {
		if (!eventMap.isEmpty())
		{
//			Object [] list = new Object[sizeEventMap(eventMap)];
			ArrayList<Event> list = new ArrayList<Event>();
			for(DateTime key: eventMap.keySet())
			{
				for(Event e: eventMap.get(key))
				{		
					list.add(e);	
				}
			}	
			return list.toArray();
		}
	
		Object [] result = null;
		return result;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		if (!eventMap.isEmpty())
		{
			ArrayList<Event> list = new ArrayList<Event>();
			
			for(DateTime key: eventMap.keySet())
			{
				for(Event e: eventMap.get(key))
				{
					for(int i=0; i<sizeEventMap(eventMap); i++)
					{
						System.out.println("[");
						list.add(e);
						System.out.println("]");
					}
				}
			}	
			return list.toArray(a);
		}
	
		T [] result = null;
		return result;
	}
	
	
	// Private methods for retainAll
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
	
	private int sizeEventMap(TreeMap<DateTime, HashSet<Event>> map) {
		
		int count =0;

		if (!map.isEmpty())
		{
			for(HashSet<Event> set: map.values())
			{
				count += set.size();
			}
				return count;
		}
		return 0;
	}
	
	public String what(){
		return arrayToString(eventMap);
	}
	
	private String arrayToString(TreeMap<DateTime, HashSet<Event>> map){
		
		StringBuilder sb = new StringBuilder();
		sb.append("Timeline: ");
		for(DateTime key: map.keySet())
			for(Event e: map.get(key))
				sb.append(e).append(" ");
		return sb.toString();
	}
		
//	public Event startedDuring(DateTime timeStart, DateTime timeEnd){
//		return 
//	}
}
