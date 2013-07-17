package codesample.timeline;

import java.util.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Timeline implements Collection<Event> 
{
//	public static final DateTimeFormatter fmt = DateTimeFormat.forPattern("mm/dd/yyyy  kk:mm"); //"month/day/year  24hour:minutes of the hour"
	
	private TreeMap<DateTime, HashSet<Event>> eventMap = new TreeMap<DateTime, HashSet<Event>>();
	
	@Override
	public boolean add(Event e) {
		return addToEventMap(eventMap, e);
	}
	
	@Override
	public boolean addAll(Collection<? extends Event> c) {
		
		if (c !=null)
		{
			Iterator<? extends Event> eventIter = c.iterator();
			while (eventIter.hasNext())    // While there is something in the collection
				this.add(eventIter.next()); // Add is to the eventMap
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		eventMap.clear(); // Clears the map
	}

	@Override
	public boolean contains(Object o) { // Boolean that returns true if the eventMap contains that Object
		
		if (o==null || !(o instanceof Event))
		{
			return false;
		}
		
		Event myEvent = (Event)o;
		DateTime startTime = myEvent.getStart();
		
		if (eventMap.containsKey(startTime)) // Check to see if the key is there.
		{
			if (eventMap.get(startTime).contains(myEvent)) // Then check if the event is there
			{
				return true;
			}
			else
				return false;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) { // Returns true if eventMap containsAll of specified collection
		
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
	public Iterator<Event> iterator()
	{
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
        private int eventSetIndex = 0;  // Keeps track of which set you are currently on
        private int eventIndex = 0;     // Keeps track of which index inside the set you are on
        private int eventsPosition = 1; // Keeps track of nextIndex()/previousIndex()
        
        private HashSet<Event> eventSet; // Represents each individual set
        private Event[] events;          // Represents an array of all sets for printing in next()/previous()
        
        Object[] eventSets = new Object[0];
          
        protected TimelineIterator()
        {
            if (eventMap != null && !eventMap.isEmpty())
            {
                Collection<HashSet<Event>> valueCollection = eventMap.values();
                if (valueCollection != null)
                {
                    eventSets = valueCollection.toArray(); // Creates an array of all the values in eventMap
                }
            }            
        }
        
		@SuppressWarnings("unchecked")
		@Override
        public boolean hasNext() 
        {
        	
        	if (eventSetIndex >= eventSets.length) { // If there are no more sets, retufn false
        		return false;
        	}
        	
        	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
       	        	
            if (eventIndex < eventSet.size()) // if the current index in set is less than the size, return true
            {
               return true;
            }
            
            if(eventSets.length-1 == eventSetIndex && eventIndex==eventSet.size()) // If you are on the last element, return false
            {
            	return false;
            }
            else {
            	if (eventSetIndex < eventSets.length) { //If there is another set, return true
            		return true;
            	}
            	else {
            		return false;
            	}
            }
            
        }

        
		@SuppressWarnings("unchecked")
		@Override
        public Event next()
        {
        	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
        	events = (Event[]) eventSet.toArray(new Event[0]);
        	Event nextEvent = null;
      
            if (eventIndex < events.length) // If you are anywhere in a set, move forward
            {
               nextEvent = events[eventIndex];
               eventIndex++;
               eventsPosition++;
            } else {
            	eventIndex = 0;
            	eventSetIndex ++;
            	eventsPosition++;
            	if (eventSetIndex < eventSets.length) {
            		HashSet<Event> eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            		Event[] events = (Event[]) eventSet.toArray(new Event[0]);
            		nextEvent = events[eventIndex];
            		eventIndex++;
            		eventsPosition++;
            	} else {
                    throw new NoSuchElementException();
            	}
            }
            
            if(eventSets.length-1 == eventSetIndex && eventIndex==eventSet.size()) // If you are on the very last element, stop
            {
            	return nextEvent;
            }
              
            if (eventIndex >= events.length) { // If you moved to the end of a set, move to the next set
            	eventIndex = 0;
            	eventSetIndex ++;
//            	eventsPosition++;
            }
            return nextEvent;
        }

		@Override
        public boolean hasPrevious()
        {
        	
        	if (eventSetIndex==0 && eventIndex==0) // First element of first set, return false
        	{
        		return false;
        	}
        	
            if (eventIndex > 0) // Anywhere in Eventsets that isnt the first value
            {
               return true;
            }
            else {
            	if (eventSetIndex > 0) { // there is a previous set
            		return true;
            	}
            	else {
            		return false;
            	}
            }
        }

		@SuppressWarnings("unchecked")
		@Override
        public Event previous() 
        {
			eventSet = (HashSet<Event>)eventSets[eventSetIndex];
        	events = (Event[]) eventSet.toArray(new Event[0]);
        	Event previousEvent = null;
        	
        	if (eventSetIndex==0 && eventIndex==0)
        	{
        		throw new NoSuchElementException();
        	}
      
            if (eventIndex > 0) // If you are anywhere that isnt the first element, move back one
            {
               eventIndex--;
               eventsPosition--;
               previousEvent = events[eventIndex];
            } else {
            	eventSetIndex --;
            	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            	eventIndex = eventSet.size();
            	if (eventSetIndex > 0) {
            		eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            		events = (Event[]) eventSet.toArray(new Event[0]);
            		eventIndex--;
            		eventsPosition--;
            		previousEvent = events[eventIndex];
            	}
            }
            
            if (eventSetIndex > 0 && eventIndex == 0) { // If after you moved back you are on the first element of a set, go back to the last element of the previous set
            	eventSetIndex --;
            	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            	eventIndex = eventSet.size();     	
            }
            return previousEvent;
        }
       
		@Override
        public int nextIndex() 
		{
        	return eventsPosition;
        }

		@Override
        public int previousIndex() {
            return eventsPosition-1; // Since eventsPosition is declared at 1 subtract one when going backwards
        }

		@Override
        public void remove()
        {
			throw new UnsupportedOperationException(
                    "We're not implementing remove!");
        }

        @Override
        public void set(Event e)
        {
            throw new UnsupportedOperationException(
                    "We're not implementing set!");
        }

        @Override
        public void add(Event e)
        {
        	throw new UnsupportedOperationException(
                    "We're not implementing add!");
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
	public boolean retainAll(Collection<?> c) { // eventMap keeps all values in collection
		
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
		return sizeEventMap(eventMap); // calls on the private size method
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
	
	
	// Private add method for any TreeMap (Used in retainAll)
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
	
	// Private size method for any TreeMap (Used in retainAll)
	private int sizeEventMap(TreeMap<DateTime, HashSet<Event>> map) {
		
		int count =0; // Count variable to keep track of all the values in eventMap

		if (!map.isEmpty())
		{
			for(HashSet<Event> set: map.values())
			{
				count += set.size();
			}
				return count;
		}
		return 0; // If it is empty return size of 0
	}
	public String toString() //For Printing out the timeline
	{
		StringBuilder sb = new StringBuilder();
		for (DateTime key: eventMap.keySet()) //the keys.
		{
			sb.append("[<").append(key).append(">: ");
			for (Event e: eventMap.get(key)) //gets each individual event  
			{
				sb.append("[").append(e).append("]");
				
			}
			sb.append("\n");
		}
		
		
		return sb.toString();
	}
}
