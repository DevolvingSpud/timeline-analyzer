package codesample.timeline;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTimeline 
{
	
	Timeline timeline;

	@Before
	public void setUp() throws Exception 
	{
		timeline = new Timeline();
	}

	@After
	public void tearDown() throws Exception 
	{
		timeline = null;
	}

	@Test
	public void testTimeline() {
		
		// Make sure the map was created
		assertNotNull(timeline); 
		
		// Make sure that the map is empty
		try{ 
			assertTrue(timeline.isEmpty());
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
		
		assertTrue(timeline.add(e));
//		System.out.println(timeline);
		assertTrue(timeline.add(x));
		assertTrue(timeline.add(e3));
		assertTrue(timeline.add(e4));
		assertTrue(timeline.add(e5));
//		System.out.println(timeline);
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
		
		assertTrue(timeline.addAll(list));
		
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
		
		timeline.add(a);
		timeline.add(b);
		timeline.add(c);
		
		timeline.clear();
		
		assertTrue(timeline.isEmpty());
	}
	
	@Test
	public void testContains(){
		
		DateTime start = new DateTime(2004,12,25,0,0);
		Event e = new NamedEvent("New Event",start);
		timeline.add(e);
		
		assertTrue(timeline.contains(e));
	}
	
	@Test
	public void testContainsAll(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,11,25,0,0);
		DateTime time2 = new DateTime(2001,10,25,0,0);
		
		Event a = new NamedEvent("EventA", time);
		Event b = new NamedEvent("EventB", start);
		Event c = new NamedEvent("EventC", start);
		
		timeline.add(a);
		timeline.add(b);
		timeline.add(c);
		
		ArrayList<Event> list = new ArrayList<Event>();
		list.add(a);
		list.add(b);
		list.add(c);
		
		assertTrue(timeline.containsAll(list));
	}
	
	
	@Test
	public void testisEmpty(){
		assertTrue(timeline.isEmpty());
	}
	
	@Test
	public void testRemove(){
		
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		
		Event e = new NamedEvent("Event", start);
		Event a = new NamedEvent("EventA", time);
		
		assertTrue(timeline.add(e));
//		assertTrue(eventMap.add(a));
		assertTrue(timeline.remove(e));
		assertTrue(timeline.isEmpty());
		
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
		
		assertTrue(timeline.add(e));
		assertTrue(timeline.add(a));
		assertTrue(timeline.add(c));
		assertTrue(timeline.removeAll(list));
		
//		assertFalse(eventMap.contains(c));
//		assertTrue(eventMap.isEmpty());
		
	}
	
	@Test
	public void testRetainAll(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		DateTime time2 = new DateTime(2003,9,25,0,0);
		
		Event a = new NamedEvent("EventE", start);
		Event b = new NamedEvent("EventA", time);
		Event c = new NamedEvent("EventC", time);
		Event d = new NamedEvent("EventD", time2);
	
		ArrayList<Event> list = new ArrayList<Event>();
		list.add(a);
		list.add(b);
//		list.add(c);
//		list.add(d);
		
		assertTrue(timeline.add(a));
		assertTrue(timeline.add(b));
		assertTrue(timeline.add(c));
		
		assertEquals(3,timeline.size());
			
		assertTrue(timeline.retainAll(list));
//		assertFalse(timeline.retainAll(list));
		
		assertTrue(timeline.contains(a));
		assertTrue(timeline.contains(b));
		assertFalse(timeline.contains(c));
	}
	
	@Test
	public void testSize(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		Event e = new NamedEvent("Event", start);
		Event a = new NamedEvent("EventA", start);
		Event c = new NamedEvent("EventC", time);
		
		assertTrue(timeline.add(e));
		assertTrue(timeline.add(a));
		assertTrue(timeline.add(c));
		
		assertEquals(3,timeline.size());
	}
	
	@Test
	public void testToArray(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		DateTime time2 = new DateTime(2003,9,25,0,0);
		
		Event a = new NamedEvent("Event", start);
		Event b = new NamedEvent("EventA", time);
		Event c = new NamedEvent("EventC", time2);
	
		timeline.add(a);
		timeline.add(b);
		timeline.add(c);
		
//		System.out.println(timeline.toArray());
//		System.out.println(java.util.Arrays.toString(timeline.toArray()));
//		System.out.print(timeline.what());
	}
	
	@Test
	public <T> void testToArrayT(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		DateTime time2 = new DateTime(2003,9,25,0,0);
		
		Event a = new NamedEvent("Event", start);
		Event b = new NamedEvent("EventA", time);
		Event c = new NamedEvent("EventC", time2);
	
		timeline.add(a);
		timeline.add(b);
		timeline.add(c);
		
		T[] result =(T[]) timeline.toArray();
		
//		System.out.println(java.util.Arrays.toString(result));
	}
	
	@Test
	public void testHasNextElement(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(1993,8,14,0,0);
		DateTime time2 = new DateTime(1985,9,12,0,0);
		
	
		
		//Case 1
//		Event a = new NamedEvent("EventA", start);
//		Event b = new NamedEvent("EventB", start);
//		Event c = new NamedEvent("EventC", start);
//		timeline.add(a);
//		timeline.add(b);
//		timeline.add(c);
//		Iterator<Event> i = timeline.iterator();
//		while(i.hasNext())
//		{
//			System.out.println(i.next()+"\n");
//		}
//		assertTrue(i.hasNext());
//		i.next();
//		i.next();
////		System.out.println(i.next());
//		assertTrue(i.hasNext());
		
		
//		
				
	}
	@Test
	public void testHasNextSet(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(1993,8,14,0,0);
		DateTime time2 = new DateTime(1985,9,12,0,0);
		//Case 2
//		Event a = new NamedEvent("EventA", start);
//		Event b = new NamedEvent("EventB", time);
//		Event c = new NamedEvent("EventC", time2);
//		timeline.add(a);
//		timeline.add(b);
//		timeline.add(c);
//		Iterator<Event> i = timeline.iterator();
//		while(i.hasNext())
//		{
//			System.out.println(i.next()+"\n");
//		}
//		assertTrue(i.hasNext());
//		System.out.println(i.next());
//		i.next();
//		assertTrue(i.hasNext());
//		System.out.println(i.next());
//		i.next();
//		System.out.println(i.next());
	}
	
	@Test
	public void testHasNextNoElement(){
		
		DateTime start = new DateTime(2004,12,25,0,0);
		//Case 3
		Event a = new NamedEvent("EventA", start);
		timeline.add(a);
		Iterator<Event> i = timeline.iterator();
		assertTrue(i.hasNext());
		i.next();
		assertFalse(i.hasNext());
		
//		try{
//			i.next();
//			fail("fail");
//		}
//		catch(NoSuchElementException e){}
	}

	@Test 
	public void testNext(){
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		DateTime time2 = new DateTime(2003,9,25,0,0);
		
		Event a = new NamedEvent("Event", start);
		Event b = new NamedEvent("EventA", time);
		Event c = new NamedEvent("EventC", time2);
	
		timeline.add(a);
//		timeline.add(b);
//		timeline.add(c);
		
		Iterator<Event> i = timeline.iterator();
		i.next();
//		i.next();
//		i.next();
	}
	
	
	////****Tests for testHasPrevious()****\\\\
	
	@Test
	public void testHasPreviousOnFirstEventIndex() //case3 //supposed to be a wrong situation.
	{
		DateTime start = new DateTime(2004,12,25,0,0);
		Event a = new NamedEvent("Event", start);
		timeline.add(a);
		//<2004,12,25,0,0> : [Event]
	
		ListIterator<Event> i = timeline.listiterator();
		assertFalse(i.hasNext());
	}
	
	@Test
	public void testHasPreviousEventIndex() //case1 //supposed to be a right situation.
	{

		DateTime time = new DateTime(2003,12,25,0,0);
		Event b = new NamedEvent("EventA", time);
		Event c = new NamedEvent("EventC", time);
		timeline.add(b);
		timeline.add(c);
		//<2003,12,25,0,0> : [EventA][EventC]
		
		ListIterator<Event> i = timeline.listiterator();
		
		i.next();
		assertTrue(i.hasPrevious());
	}
	
	@Test
	public void testHasPreviousEventSet() //case2 //supposed to be a right situation.
	{
		DateTime start = new DateTime(2004,12,25,0,0);
		DateTime time = new DateTime(2003,12,25,0,0);
		//DateTime time2 = new DateTime(2003,9,25,0,0);
		Event a = new NamedEvent("Event", start);
		Event b = new NamedEvent("EventA", time);
		timeline.add(a);
		timeline.add(b);
		
		//<2004,12,25,0,0> : [Event]
		//<2003,12,25,0,0> : [EventA]
		
		ListIterator<Event> i = timeline.listiterator();
		
		i.next();
		assertTrue(i.hasPrevious());
	}
	
	////****Tests for testPrevious()****\\\\
	
	
	@Test
	public void testNextIndex(){
		
	}
	
	@Test
	public void testPreviousIndex(){
		
	}
	
	@Test
	public void testIterRemove(){
		
	}
	
	@Test
	public void testIterAdd(){
		
	}
}
