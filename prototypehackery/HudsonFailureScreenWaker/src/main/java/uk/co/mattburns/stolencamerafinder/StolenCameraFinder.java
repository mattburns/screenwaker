package uk.co.mattburns.stolencamerafinder;

import java.util.Date;

/**
 * This will probably be the main application
 * 
 * @author matt
 * 
 */
public interface StolenCameraFinder {

    public static final Date DEFAULT_MIN_DATE = new Date(0);
    public static final Date DEFAULT_MAX_DATE = new Date();

    /**
     * When passing tags to use for the searches, this chooses the "OR" or "AND" criteria
     * 
     * @author matt
     * 
     */
    public enum TAG_SEARCH_MODE {
        all, any
    }

    /**
     * Find a photo with the given list of tags and taken with the camera with the given serial number
     * 
     * @param photoTags
     * @param tagMode
     * @return Photo object of first photo found that matched serach criteria. You can, for example, then retrieve the
     *         url from that object.
     */
    void findPhoto(String[] photoTags, TAG_SEARCH_MODE tagMode, SearchObserver controller);

    /**
     * Find a photo with the given list of tags and taken with the camera with the given serial number, within the given
     * date range
     * 
     * @param photoTags
     * @param tagMode
     * @param minTakenDate
     *            Only search for photos taken after this date (exclusive)
     * @param maxTakenDate
     *            Only search for photos taken before this date (exclusive)
     * @return Photo object of first photo found that matched serach criteria. You can, for example, then retrieve the
     *         url from that object.
     */
    void findPhoto(String[] photoTags, TAG_SEARCH_MODE tagMode, Date minTakenDate, Date maxTakenDate,
            SearchObserver controller);

    void setStolenCameraDatabase(StolenCameraDatabase database);

    StolenCameraDatabase getStolenCameraDatabase();
}
