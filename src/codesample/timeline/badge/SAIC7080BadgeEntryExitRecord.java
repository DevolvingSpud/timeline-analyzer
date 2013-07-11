/**
 * 
 */
package codesample.timeline.badge;

import java.util.ListIterator;

/**
 * A BadgeEntryExitRecord implementation built to handle the SAIC facility
 * problem.
 * 
 * @author Lamont
 * 
 */
public class SAIC7080BadgeEntryExitRecord extends BadgeEntryExitRecord {

	/**
	 * Constructor.
	 * 
	 * @param badgeIter
	 *            The iterator of BadgeEvent objects to be used for this class.
	 */
	public SAIC7080BadgeEntryExitRecord(ListIterator<BadgeEvent> badgeIter) {
		super(badgeIter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * codesample.timeline.badge.BadgeEntryExitRecord#isEntry(codesample.timeline
	 * .badge.BadgeEvent)
	 */
	@Override
	protected boolean isEntry(BadgeEvent event) {
		return ((event.getLocation() != null) && (event.getLocation().contains(
				"7080 BLDG ENTRY") || event.getLocation().contains(
				"7080 SCIF ENTRY")));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * codesample.timeline.badge.BadgeEntryExitRecord#isExit(codesample.timeline
	 * .badge.BadgeEvent)
	 */
	@Override
	protected boolean isExit(BadgeEvent event) {
		return ((event.getLocation() != null) && (event.getLocation().contains(
				"SCIF EXIT") || event.getLocation().contains("SCIF C T. EXIT")));
	}

}
