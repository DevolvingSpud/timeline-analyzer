/**
 * 
 */
package codesample.timeline.badge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * A BadgeEntry reader from the SAIC v1 logs.
 * <p>
 * These badge entries are in the following format: MM/DD/YYYY HH:MM:SS
 * <entry-type> <person's name> at <location they were let into>
 * 
 * @author Lamont
 * 
 */
public class SAICBadgeReaderv1 {

	public static BadgeEvent createEvent(String line)
			throws IllegalArgumentException {
		Scanner lineScanner = new Scanner(line).useDelimiter(" ");
		String[] datePieces = lineScanner.next().split("/");
		String[] timePieces = lineScanner.next().split(":");

		int month = Integer.parseInt(datePieces[0]);
		int day = Integer.parseInt(datePieces[1]);
		int year = Integer.parseInt(datePieces[2]);
		int hours = Integer.parseInt(timePieces[0]);
		int minutes = Integer.parseInt(timePieces[1]);
		int seconds = Integer.parseInt(timePieces[2]);

		DateTime eventTime = new DateTime(year, month, day, hours, minutes,
				seconds);

		// Get the next string. If it starts with a bracket, it is a duplicate
		// time and can be ignored.
		String strEventType = lineScanner.next();
		if (strEventType.startsWith("[")) {
			while (!strEventType.endsWith("]")) {
				strEventType = lineScanner.next();
			}

			strEventType = lineScanner.next();

		}

		BadgeEvent.Type eventType = BadgeEvent.Type.ADMITTED;
		if (strEventType.equalsIgnoreCase(BadgeEvent.Type.REJECTED.toString())) {
			eventType = BadgeEvent.Type.REJECTED;
		} else if (!strEventType.equalsIgnoreCase(BadgeEvent.Type.ADMITTED
				.toString())) {
			throw new IllegalArgumentException(
					"Found unexpected event type of " + strEventType);
		}

		// Get the person's name
		StringBuilder sb = new StringBuilder();
		String nextString = lineScanner.next();
		while (!nextString.equalsIgnoreCase("AT")) {
			sb.append(nextString).append(" ");
			nextString = lineScanner.next();
		}
		String name = sb.toString().trim();

		// Get the location
		sb = new StringBuilder();
		while (lineScanner.hasNext()) {
			sb.append(lineScanner.next()).append(" ");
		}
		String location = sb.toString();

		return new BadgeEvent(name, eventType, location, eventTime);
	}

	/**
	 * Creates a list of Events given an input file
	 * 
	 * @param inputFile
	 *            inputFile
	 * @return the list of events created from the file
	 * @exception IOException
	 *                if there is a problem with the file itself
	 */
	public static List<BadgeEvent> createEventList(File inputFile)
			throws IOException {

		Scanner inScan = new Scanner(inputFile);
		List<BadgeEvent> eventList = new LinkedList<BadgeEvent>();
		for (String line = inScan.nextLine(); inScan.hasNextLine(); line = inScan
				.nextLine()) {
			try {
				eventList.add(createEvent(line));
			} catch (Exception e) {
				System.out.println("Error trying to create BadgeEvent from -->"
						+ line + "<--: " + e.getMessage());

			}
		}
		Collections.sort(eventList);
		return eventList;

	}

	/**
	 * Creates a list of BadgeEntryExitRecord objects based on the raw events
	 * 
	 * @param rawEvents
	 *            the raw events
	 * @return the list of BadgeEntryExitRecord objects
	 */
	public static List<BadgeEntryExitRecord> createEntryExitRecords(
			List<BadgeEvent> rawEvents) {

		List<BadgeEntryExitRecord> entryExitRecords = new LinkedList<BadgeEntryExitRecord>();

		// Sort the event list
		Collections.sort(rawEvents);
		ListIterator<BadgeEvent> eventIter = rawEvents.listIterator();
		while (eventIter.hasNext()) {
			entryExitRecords.add(new SAIC7080BadgeEntryExitRecord(eventIter));
		}

		return entryExitRecords;
	}

	/**
	 * Create a list of the daily records, given entry records.
	 * 
	 * @param entryRecords
	 *            The entry records
	 * @return a list of daily records
	 */
	public static List<DailyBadgeRecord> createDailyRecords(
			List<BadgeEntryExitRecord> entryRecords) {
		Map<LocalDate, DailyBadgeRecord> dailyMap = new HashMap<LocalDate, DailyBadgeRecord>();
		for (BadgeEntryExitRecord rec : entryRecords) {
			if (!dailyMap.containsKey(rec.getLocalDate())) {
				dailyMap.put(rec.getLocalDate(),
						new DailyBadgeRecord(rec.getLocalDate()));
			}
			dailyMap.get(rec.getLocalDate()).addEntryRecord(rec);
		}

		List<DailyBadgeRecord> dailyRecords = new LinkedList<DailyBadgeRecord>(
				dailyMap.values());
		Collections.sort(dailyRecords);
		return dailyRecords;
	}

	/**
	 * Test out the badge reader.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String line = "04/06/2012 11:57:39 Admitted Foster, John L. at 7080 SCIF EXIT [In]";
		BadgeEvent bEvent = createEvent(line);
		System.out.println("The event is " + bEvent);

		try {

			System.out.println("Creating BadgeEvent list from file...");
			List<BadgeEvent> rawEvents = createEventList(new File(
					"data\\rawbadgedata.txt"));
			System.out.println("Successfully created " + rawEvents.size()
					+ " badge events for list");

			// Create the Entry/Exit records
			List<BadgeEntryExitRecord> eeRecords = createEntryExitRecords(rawEvents);
			System.out.println("I created " + eeRecords.size()
					+ " records from the initial badge list");

			// Create the Daily records
			List<DailyBadgeRecord> dailyRecords = createDailyRecords(eeRecords);
			System.out.println("I created "+dailyRecords.size()+" daily records from the initial badge list");
			for (DailyBadgeRecord dailyRec : dailyRecords) {
				System.out.println(dailyRec);
			}
			
			// Creating an output report on the cheap
			File statsFile = new File("DailyStatsReport.txt");
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(statsFile)));
			ReportGenerator.generateDailyStatsReport(dailyRecords, out);
			out.flush();
			out.close();
			
			File gapsFile = new File("GapsReport.txt");
			PrintWriter gapOut = new PrintWriter(new BufferedWriter(new FileWriter(gapsFile)));
			ReportGenerator.generateGapsReport(dailyRecords, gapOut);
			gapOut.flush();
			gapOut.close();
			System.out.println("Created an output report in "+statsFile.getAbsolutePath());
			// Map<LocalDate, List<BadgeEvent>> eventMap = createEventList(new
			// File(
			// "data\\rawbadgedata.txt"));
			// System.out
			// .println("I successfully created an eventMap that covers "
			// + eventMap.keySet().size()
			// + " days out of file events");
			// int numEvents = 0;
			// for (List<BadgeEvent> eventSet : eventMap.values()) {
			// numEvents += eventSet.size();
			// }
			//
			// StringBuilder sb = new StringBuilder();
			// for (LocalDate date : eventMap.keySet()) {
			// List<BadgeEvent> dayEntries = eventMap.get(date);
			// Collections.sort(dayEntries);
			// sb.append(date).append(" has ").append(dayEntries.size())
			// .append(" entries:\n");
			// for (BadgeEvent entry : dayEntries) {
			// sb.append("\t").append(entry).append("\n");
			// }
			// }
			// System.out.println("My map includes " + numEvents + " events");
			// System.out.println(sb.toString());
		} catch (IOException ioe) {
			System.out.println("Could not create events from File: "
					+ ioe.getMessage());
		}
	}

}
