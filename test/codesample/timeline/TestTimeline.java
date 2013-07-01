package codesample.timeline;

import static org.junit.Assert.*;

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
		
		Event e = new NamedEvent("Event",DateTime.now());
		assertTrue(eventMap.add(e));
	}
	
	@Test
	public void testAddAll(){
		
	}
	
	@Test
	public void testContains(){
		
		Event e = new NamedEvent("New Event",DateTime.now());
		eventMap.add(e);
		
		assertTrue(eventMap.contains(e));
		
	}
	
	@Test
	public void testContainsAll(){
		
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
