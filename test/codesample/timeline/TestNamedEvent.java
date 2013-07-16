package codesample.timeline;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNamedEvent {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateNamedEvent() {
		DateTime start = DateTime.now();
		String name = "Event";
		
		NamedEvent e = new NamedEvent(name, start);
		assertEquals(name, e.getName());
		assertEquals(start, e.getStart());
	}
	
	@Test
	public void testNamedEventComparatorSameEvent() {
		NamedEvent event1 = new NamedEvent("1", new DateTime(1));
		NamedEvent event2 = new NamedEvent("1", new DateTime(1));
		
		assertTrue(event1.compareTo(event2) == 0);
		assertTrue(event2.compareTo(event1) == 0);
	}
	
	@Test
	public void testNamedEventComparatorSameTimeDifferentNames() {
		NamedEvent event1 = new NamedEvent("1", new DateTime(1));
		NamedEvent event2 = new NamedEvent("2", new DateTime(1));
		
		assertTrue(event1.compareTo(event2) < 0);
		assertTrue(event2.compareTo(event1) > 0);
	}
	
	@Test
	public void testNamedEventComparatorSameStartDifferentEnds() {
		NamedEvent event1 = new NamedEvent("1", new DateTime(1), new DateTime(2));
		NamedEvent event2 = new NamedEvent("2", new DateTime(1), new DateTime(3));
		
		assertTrue(event1.compareTo(event2) < 0);
		assertTrue(event2.compareTo(event1) > 0);
	}
	
	@Test
	public void testNamedEventComparatorDifferentStartSameEnds() {
		NamedEvent event1 = new NamedEvent("1", new DateTime(1), new DateTime(3));
		NamedEvent event2 = new NamedEvent("2", new DateTime(2), new DateTime(3));
		
		assertTrue(event1.compareTo(event2) < 0);
		assertTrue(event2.compareTo(event1) > 0);
	}

	@Test
	public void testNamedEventComparatorOverlaps() {
		NamedEvent event1 = new NamedEvent("1", new DateTime(1), new DateTime(3));
		NamedEvent event2 = new NamedEvent("2", new DateTime(2), new DateTime(5));
		NamedEvent event3 = new NamedEvent("3", new DateTime(3), new DateTime(4));
		NamedEvent event4 = new NamedEvent("4", new DateTime(3), new DateTime(5));
		
		assertTrue(event1.compareTo(event2) < 0);
		assertTrue(event2.compareTo(event1) > 0);

		assertTrue(event2.compareTo(event3) < 0);
		assertTrue(event3.compareTo(event2) > 0);

		assertTrue(event3.compareTo(event4) < 0);
		assertTrue(event4.compareTo(event3) > 0);	
	}
	
	@Test
	public void testNamedEventComparatorDisjoint() {
		NamedEvent event1 = new NamedEvent("1", new DateTime(1), new DateTime(2));
		NamedEvent event2 = new NamedEvent("2", new DateTime(3), new DateTime(4));
		
		assertTrue(event1.compareTo(event2) < 0);
		assertTrue(event2.compareTo(event1) > 0);

	}
	
}
