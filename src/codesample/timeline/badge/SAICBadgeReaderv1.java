/**
 * 
 */
package codesample.timeline.badge;

import java.util.Scanner;

import org.joda.time.DateTime;

/**
 * A BadgeEntry reader from the SAIC v1 logs.
 * <p>
 * These badge entries are in the following format:
 * MM/DD/YYYY HH:MM:SS <entry-type> <person's name> at <location they were let into>
 * @author Lamont
 *
 */
public class SAICBadgeReaderv1 {
	
	public static BadgeEvent createEvent(String line) throws IllegalArgumentException {
		Scanner lineScanner = new Scanner(line).useDelimiter(" ");
		int month = lineScanner.nextInt();
		int day = lineScanner.nextInt();
		int year = lineScanner.nextInt();
		int hours = lineScanner.nextInt();
		int minutes = lineScanner.nextInt();
		int seconds = lineScanner.nextInt();
		
		DateTime eventTime = new DateTime(year, month, day, hours, minutes, seconds);
		
		// Get the event type
		String strEventType = lineScanner.next();
		BadgeEvent.Type eventType = BadgeEvent.Type.ADMITTED;
		if (strEventType.equalsIgnoreCase(BadgeEvent.Type.REJECTED.toString())) {
			eventType = BadgeEvent.Type.REJECTED;
		} else if (!strEventType.equalsIgnoreCase(BadgeEvent.Type.ADMITTED.toString())) {
			throw new IllegalArgumentException("Found unexpected event type of "+strEventType);
		}
		
		// Get the person's name
		StringBuilder sb = new StringBuilder();
		String nextString = lineScanner.next();
		while (!nextString.equalsIgnoreCase("AT")){
			sb.append(nextString).append(" ");
		}
		String name = sb.toString().trim();
		
		// Get the location
		sb = new StringBuilder();
		while (lineScanner.hasNext()){
			sb.append(lineScanner.next()).append(" ");
		}
		String location = sb.toString();
		
		return new BadgeEvent(name, eventType, location, eventTime);

	}

}
