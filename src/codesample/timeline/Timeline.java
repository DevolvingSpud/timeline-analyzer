package codesample.timeline;

import java.lang.reflect.Array;
import java.util.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Timeline implements Collection<Event> 
{
	public static final DateTimeFormatter fmt = DateTimeFormat.forPattern("mm/dd/yyyy  kk:mm"); //"month/day/year  24hour:minutes of the hour"
	
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
        private int eventSetIndex = 0;
        private int eventIndex = 0;
        
        Object[] eventSets = new Object[0];
        
		
                    
        protected TimelineIterator()
        {
            if (eventMap != null && !eventMap.isEmpty())
            {
                Collection<HashSet<Event>> valueCollection = eventMap.values();
                if (valueCollection != null)
                {
                    eventSets = valueCollection.toArray();
                }
            }            
        }
        
        private HashSet<Event> eventSet;
        private Event[] events;

		
		@SuppressWarnings("unchecked")
		@Override
        public boolean hasNext() 
        {
        	
        	if (eventSetIndex >= eventSets.length) {
        		return false;
        	}
        	
        	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
       	        	
            if (eventIndex < eventSet.size())
            {
               return true;
            }
            if(eventSets.length-1 == eventSetIndex && eventIndex==eventSet.size())
            {
            	return false;
            }
            else {
            	if (eventSetIndex < eventSets.length) {
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
      
            if (eventIndex < events.length)
            {
               nextEvent = events[eventIndex];
               eventIndex++;
            } else {
            	eventIndex = 0;
            	eventSetIndex ++;
            	if (eventSetIndex < eventSets.length) {
            		HashSet<Event> eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            		Event[] events = (Event[]) eventSet.toArray(new Event[0]);
            		nextEvent = events[eventIndex];
            		eventIndex++;
            	} else {
                    throw new NoSuchElementException();
            	}
            }
            
            if(eventSets.length-1 == eventSetIndex && eventIndex==eventSet.size())
            {
            	return nextEvent;
            }
              
            if (eventIndex >= events.length) {
            	eventIndex = 0;
            	eventSetIndex ++;          	
            }
            return nextEvent;
        }

		@Override
        public boolean hasPrevious()
        {
        	
        	if (eventSetIndex==0 && eventIndex==0)
        	{
        		return false;
        	}
        	
            if (eventIndex > 0)
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
      
            if (eventIndex > 0)
            {
               eventIndex--;  
               previousEvent = events[eventIndex];
            } else {
            	eventSetIndex --;
            	eventIndex = eventSet.size();
            	if (eventSetIndex > 0) {
            		eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            		events = (Event[]) eventSet.toArray(new Event[0]);
            		eventIndex--;
            		previousEvent = events[eventIndex];
            	}
            }
            
            if (eventSetIndex > 0 && eventIndex == 0) {
            	eventSetIndex --;
            	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            	eventIndex = eventSet.size();     	
            }
            
            return previousEvent;
        }
        

		//code is probably wrong.
		@Override
        public int nextIndex() //of the all the events or just the events in a particular set.
        {
<<<<<<< HEAD

=======
>>>>>>> 2eed06594cf9c8093077d6f0a3353e3582aa1d82
			HashSet<Event> eventSet = (HashSet<Event>)eventSets[eventSetIndex]; //set for the 0th eventSet.
        	Event[] events = (Event[]) eventSet.toArray(new Event[0]);
        	
        	int nextEventIndex = 0;
        	Event nextEvent = null;
            if (eventIndex < events.length)
            {
               nextEventIndex++;
               eventIndex++;
            } 
            else 
            {
            	eventIndex = 0;
            	eventSetIndex++;
            	
            	if (eventSetIndex < eventSets.length) 
            	{
            		eventSet = (HashSet<Event>)eventSets[eventSetIndex]; //set for the 1th...nth eventSet.
            		events = (Event[]) eventSet.toArray(new Event[0]);
            		
            		eventIndex++;
            	} 
            	//if (eventSetIndex == eventSets.length && eventIndex == events.length) //if 'pointer' is at end of list, return the size of the list, which is one more than the last index.
            	else
            	{
                    nextEventIndex++;
                    return nextEventIndex;  //returns the size of all the events => number of the events.
            	}
            }
            
            if (eventIndex >= events.length) 
            {
            	eventIndex = 0;
            	eventSetIndex ++;          	
            }
            return nextEventIndex;
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
	
	// Private size method 
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
	public String toString() // try to use 'fmt' to later better the format of the keys.
	{
		StringBuilder sb = new StringBuilder();
		//sb.append(thing you want to append on to the string);
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
