package uk.co.mattburns.stolencamerafinder;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

public class StolenCameraDatabaseImpl implements StolenCameraDatabase {

    /** Map from serial numbers to StolenCamera objects */
    private final SortedMap<String, StolenCamera> stolenCameras = new TreeMap<String, StolenCamera>();

    @Override
    public void addStolenCamera(StolenCamera stolenCamera) {
        if (!containsCamera(stolenCamera.getSerialNumber())) {
            stolenCameras.put(stolenCamera.getSerialNumber(), stolenCamera);
        }
    }

    @Override
    public void removeStolenCamera(StolenCamera stolenCamera) {
        stolenCameras.remove(stolenCamera.getSerialNumber());
    }

    @Override
    public boolean containsCamera(String serialNumber) {
        return stolenCameras.containsKey(serialNumber);
    }

    @Override
    public boolean wasPhotoTakenWhilstStolen(String serialNumber, Date takenDate) {
        if (!containsCamera(serialNumber)) {
            return false;
        } else {
            return stolenCameras.get(serialNumber).getStolenDate().before(takenDate);
        }
    }

    public int getStolenCameraCount() {
        return stolenCameras.size();
    }

    @Override
    public StolenCamera getCamera(String serialNumber) {
        return stolenCameras.get(serialNumber);
    }

}
