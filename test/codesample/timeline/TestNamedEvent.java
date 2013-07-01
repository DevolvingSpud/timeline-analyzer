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
}
