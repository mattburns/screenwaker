package uk.co.mattburns.stolencamerafinder;

import java.util.Date;

public interface StolenCameraDatabase {

    /**
     * Test if the given serial number is known to be stolen
     * 
     * @param serialNumber
     * @return return true if it's nicked
     */
    boolean containsCamera(String serialNumber);

    /**
     * Was a photo taken on this date with this serial, taken with a stolen camera, after it is reported to have been
     * stolen
     * 
     * @param serialNumber
     * @param takenDate
     * @return
     */
    boolean wasPhotoTakenWhilstStolen(String serialNumber, Date takenDate);

    /**
     * Add another stolen camera to the list of stolen cameras
     * 
     * @param stolenCamera
     */
    void addStolenCamera(StolenCamera stolenCamera);

    /**
     * Remove stolen camera from the list of stolen cameras
     * 
     * @param stolenCamera
     */
    void removeStolenCamera(StolenCamera stolenCamera);

    /**
     * Total stolen cameras on record
     * 
     * @return integer number of cameras
     */
    int getStolenCameraCount();

    /**
     * Get the StolenCameraObject in the database with the given ID
     * 
     * @param serialNumber
     * @return
     */
    StolenCamera getCamera(String serialNumber);
}
