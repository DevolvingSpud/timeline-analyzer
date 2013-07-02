package codesample.timeline;

import static org.junit.Assert.*;

import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.TimeOfDay;
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
		
		assertEquals(e,x);
		
		assertTrue(eventMap.add(e));
		assertTrue(eventMap.add(x));
	}
	
	@Test
	public void testAddAll(){
		DateTime start = new DateTime(2004,12,25,0,0);
		Event a = new NamedEvent("EventA", start);
		Event b = new NamedEvent("EventB", start);
		Event c = new NamedEvent("EventC", start);
		
		eventMap.add(a);
		
		assertTrue(Collections.addAll(eventMap,b,c));
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
		Event a = new NamedEvent("EventA", start);
		Event b = new NamedEvent("EventB", start);
		Event c = new NamedEvent("EventC", start);
		
		eventMap.add(a);
		eventMap.add(b);
		eventMap.add(c);
		
//		assertTrue(Collections.containsAll(eventMap,a,b,c));
		fail();
	}
	
	
	@Test
	public void testisEmpty(){
		assertTrue(eventMap.isEmpty());
	}
	
	@Test
	public void testClear(){
		DateTime start = new DateTime(2004,12,25,0,0);
		Event a = new NamedEvent("EventA", start);
		Event b = new NamedEvent("EventB", start);
		Event c = new NamedEvent("EventC", start);
		
		eventMap.add(a);
		eventMap.add(b);
		eventMap.add(c);
		
		eventMap.clear();
		
		assertTrue(eventMap.isEmpty());
	}
	
	@Test
	public void testRemove(){
		
		DateTime start = new DateTime(2004,12,25,0,0);
		Event e = new NamedEvent("Event", start);
		
		assertTrue(eventMap.add(e));
		assertTrue(eventMap.remove(e));
		
//		assertTrue(eventMap.isEmpty());
//		fail();
	}
	
	@Test
	public void testSize(){
		DateTime start = new DateTime(2004,12,25,0,0);
		Event e = new NamedEvent("Event", start);
		Event a = new NamedEvent("EventA", start);
		
		assertTrue(eventMap.add(e));
		assertTrue(eventMap.add(a));
		
		assertEquals(2,eventMap.size());
		
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
