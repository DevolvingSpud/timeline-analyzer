package codesample.timeline;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

/**
 * A generator for NamedEvent objects to be used for testing purposes.
 * <p>
 * The idea of the <em>NamedEventGenerator</em> is to allow large numbers of
 * events to be generated for testing purposes. The events will use a standard
 * naming convention and will be randomized between two dates, with the idea of
 * maintaining an average number of event 'collisions' at particular start
 * dates. This will allow us to properly exercise our timeline and allow us to
 * ensure that things work when dealing with large numbers of events.
 * 
 * @author Lamont
 * 
 */
public class NamedEventGenerator {

	// The default timeframe for our timeline in seconds
	public static long DEFAULT_TIMEFRAME_SECONDS = 86400L;

	// The default maximum interval in seconds
	public static long DEFAULT_MAX_INTERVAL_SECONDS = 86400L;

	// The default number of collisions to try and maintain
	public static int DEFAULT_NUM_COLLISIONS = 5;

	/**
	 * Generates a list of events given very specific criteria.
	 * <p>
	 * The events that are generated will be guaranteed to be between the two
	 * time intervals and within the given intervals. Both the timeframes and
	 * the intervals will be generated at random, and the names of the events
	 * are guaranteed to be unique.
	 * 
	 * @param fromDate
	 *            The date to start the events from
	 * @param toDate
	 *            The date that the events should go to
	 * @param eventsToGenerate
	 *            The number of events to generate
	 * @param maxInterval
	 *            The maximum interval size
	 * @param averageCollisions
	 *            The average size of the buckets. Used to guarantee collisions.
	 * @return The list of generated events
	 */
	public static List<Event> generate(DateTime fromDate, DateTime toDate,
			long eventsToGenerate, long maxInterval, int averageCollisions) {

		// Create the buckets. In order to guarantee that we get collisions, we
		// are going to create the start dates and select from the list.
		ArrayList<DateTime> dateList = new ArrayList<DateTime>();
		long bucketsToGenerate = (long) eventsToGenerate / averageCollisions;
		while (bucketsToGenerate > 0) {
			dateList.add(fromDate.plus(Math.round(Math.random()
					* (toDate.getMillis() - fromDate.getMillis()))));
			--bucketsToGenerate;
		}

		LinkedList<Event> eventList = new LinkedList<Event>();

		for (long i = 0L; i < eventsToGenerate; i++) {
			// Generate a name for you
			StringBuilder sb = new StringBuilder();
			sb.append("Generated #").append(i);

			// Determine the actual start date to use
			DateTime startDate = dateList.get((int) Math.round(Math.random()
					* (dateList.size() - 1)));

			// Determine the end date to use
			DateTime endDate = startDate.plus(Math.round(Math.random()
					* maxInterval));

			eventList.add(new NamedEvent(sb.toString(), startDate, endDate));
		}

		return eventList;
	}

	/**
	 * A static method to generate a list of events given minimal criteria.
	 * <p>
	 * The events will be generated within a day of the start date and will last
	 * for at most maxInterval.
	 * 
	 * @param fromDate
	 *            the date to generate the events from
	 * @param numToGenerate
	 *            the number of events to generate
	 * @param maxInterval
	 *            the maximum interval
	 * @return the generated list of events
	 */
	public static List<Event> generate(DateTime fromDate, long numToGenerate,
			long maxInterval) {
		return generate(fromDate,
				fromDate.plusSeconds((int) DEFAULT_TIMEFRAME_SECONDS),
				numToGenerate, maxInterval, DEFAULT_NUM_COLLISIONS);
	}

	/**
	 * A static method to generate events.
	 * <p>
	 * The events will generated within a day of fromDate and will last for at
	 * most one day.
	 * 
	 * @param fromDate
	 *            the date to generate the events from
	 * @param numToGenerate
	 *            the number of events to generate
	 * @return the generated list of events
	 */
	public static List<Event> generate(DateTime fromDate, long numToGenerate) {
		return generate(fromDate,
				fromDate.plusSeconds((int) DEFAULT_TIMEFRAME_SECONDS),
				numToGenerate, DEFAULT_MAX_INTERVAL_SECONDS,
				DEFAULT_NUM_COLLISIONS);
	}

}