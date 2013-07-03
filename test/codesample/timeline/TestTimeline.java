package codesample.timeline;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTimeline {
	
	Timeline eventMap;

	@Before
	public void setUp() throws Exception {
		eventMap = new Timeline();
	}

	@After
	public void tearDown() throws Exception {
		eventMap = null;
	}

	@Test
	public void testTimeline() {
		
		// Make sure the map was created
		assertNotNull(eventMap); 
		
		// Make sure that the map is empty
		try{ 
			assertTrue(eventMap.isEmpty());
		}
		catch(NullPointerException e){}
	}
	
	@Test
	public void testAdd(){
		
		DateTime start = new DateTime(2004,12,25,0,0);
		Event e = new NamedEvent("Event", start);
		Event x = new NamedEvent("Another Event", start);
		Event e3 = new NamedEvent("Event 3", new DateTime(2005,01,02, 0, 0));
		Event e4 = new NamedEvent("Event 4", new DateTime(2005,01,02, 0, 0));
		Event e5 = new NamedEvent("Event 5", new DateTime(2005,01,07, 0, 0));
		
		assertFalse(e.equals(x));
		
		assertTrue(eventMap.add(e));
//		System.out.println(eventMap);
		assertTrue(eventMap.add(x));
		assertTrue(eventMap.add(e3));
		assertTrue(eventMap.add(e4));
		assertTrue(eventMap.add(e5));
//		System.out.println(eventMap);
	}
	
	@Test
	public void testAddAll(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime start2 = new DateTime(2003,04,25,0,0);
		Event a = new NamedEvent("EventA", start);
		Event b = new NamedEvent("EventB", start);
		Event c = new NamedEvent("EventC", start2);
		
		ArrayList<Event> list = new ArrayList<Event>();
//		list.add(a);
//		list.add(b);
//		list.add(c);
		Collections.addAll(list,a,b,c);
		
		assertTrue(eventMap.addAll(list));
		
//		assertTrue(eventMap.contains(b));
	}
	
	@Test
	public void testClear(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		DateTime time2 = new DateTime(2002,12,25,0,0);
		Event a = new NamedEvent("EventA", start);
		Event b = new NamedEvent("EventB", time);
		Event c = new NamedEvent("EventC", time2);
		
		eventMap.add(a);
		eventMap.add(b);
		eventMap.add(c);
		
		eventMap.clear();
		
		assertTrue(eventMap.isEmpty());
	}
	
	@Test
	public void testContains(){
		
		DateTime start = new DateTime(2004,12,25,0,0);
		Event e = new NamedEvent("New Event",start);
		eventMap.add(e);
		
		assertTrue(eventMap.contains(e));
	}
	
	@Test
	public void testContainsAll(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,11,25,0,0);
		Event a = new NamedEvent("EventA", time);
		Event b = new NamedEvent("EventB", start);
//		Event c = new NamedEvent("EventB", start);
		
		eventMap.add(a);
		eventMap.add(b);
		
		ArrayList<Event> list = new ArrayList<Event>();
		list.add(a);
		list.add(b);
		
		assertTrue(eventMap.containsAll(list));
	}
	
	
	@Test
	public void testisEmpty(){
		assertTrue(eventMap.isEmpty());
	}
	
	@Test
	public void testIterator(){
		fail();
	}
	
	
	
	@Test
	public void testRemove(){
		
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		
		Event e = new NamedEvent("Event", start);
//		Event a = new NamedEvent("EventA", time);
		
		assertTrue(eventMap.add(e));
//		assertTrue(eventMap.add(a));
		assertTrue(eventMap.remove(e));
//		assertTrue(eventMap.isEmpty());
		
//		assertTrue(eventMap.remove(a));
//		assertTrue(eventMap.contains(a));
	}
	
	@Test
	public void testRemoveAll(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		DateTime time2 = new DateTime(2003,9,25,0,0);
		
		Event e = new NamedEvent("Event", start);
		Event a = new NamedEvent("EventA", time);
		Event c = new NamedEvent("EventC", time2);
	
		ArrayList<Event> list = new ArrayList<Event>();
		list.add(e);
		list.add(a);
		list.add(c);
		
		assertTrue(eventMap.add(e));
		assertTrue(eventMap.add(a));
		assertTrue(eventMap.add(c));
		assertTrue(eventMap.removeAll(list));
		
//		assertFalse(eventMap.contains(c));
//		assertTrue(eventMap.isEmpty());
		
	}
	
	@Test
	public void testRetainAll(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		DateTime time2 = new DateTime(2003,9,25,0,0);
		
		Event e = new NamedEvent("Event", start);
		Event a = new NamedEvent("EventA", time);
		Event c = new NamedEvent("EventC", time2);
	
		ArrayList<Event> list = new ArrayList<Event>();
		list.add(e);
		list.add(a);
//		list.add(c);
		
		assertTrue(eventMap.add(e));
		assertTrue(eventMap.add(a));
		assertTrue(eventMap.add(c));
		assertTrue(eventMap.retainAll(list));
		assertFalse(eventMap.contains(c));
	}
	
	@Test
	public void testSize(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		Event e = new NamedEvent("Event", start);
		Event a = new NamedEvent("EventA", time);
		
		assertTrue(eventMap.add(e));
		assertTrue(eventMap.add(a));
		
		assertEquals(2,eventMap.size());
	}
	
	@Test
	public void testToArray(){
		fail();
	}
	
	@Test
	public void testToArrayT(){
		fail();
	}

	
	@Test
	public void testStartedDuring(){
		
	}
	
	@Test
	public void testEndingDuring(){
		
	}
	
	@Test
	public void testEventBefore(){
		
	}
	
	@Test
	public void testEventAfter(){
		
	}
}
