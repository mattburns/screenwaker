package uk.co.mattburns.stolencamerafinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class StolenCameraDatabaseTest {

    @Test
    public void testDatabaseConfirmsStolenSerial() {
        StolenCameraDatabase database = new StolenCameraDatabaseImpl();
        StolenCamera camera = new StolenCamera("123", new Date());
        database.addStolenCamera(camera);

        assertTrue(database.containsCamera("123"));
    }

    @Test
    public void testDatabaseDeniesUnknownSerial() {
        StolenCameraDatabase database = new StolenCameraDatabaseImpl();
        StolenCamera camera = new StolenCamera("123", new Date(2));
        database.addStolenCamera(camera);

        assertFalse(database.containsCamera("321"));
        assertFalse(database.wasPhotoTakenWhilstStolen("321", new Date(3)));
    }

    @Test
    public void testDatabaseConfirmsStolenCameraOnDate() {
        StolenCameraDatabase database = new StolenCameraDatabaseImpl();
        StolenCamera camera = new StolenCamera("123", new Date(3));
        database.addStolenCamera(camera);
        assertTrue(database.wasPhotoTakenWhilstStolen("123", new Date(4)));
    }

    @Test
    public void testDatabaseDeniesStolenCameraBeforeDate() {
        StolenCameraDatabase database = new StolenCameraDatabaseImpl();
        StolenCamera camera = new StolenCamera("123", new Date(3));
        database.addStolenCamera(camera);
        assertFalse(database.wasPhotoTakenWhilstStolen("123", new Date(2)));
        assertFalse("Same date should not be considered stolen", database.wasPhotoTakenWhilstStolen("123", new Date(3)));
    }

    @Test
    public void testDatabaseConfirmsCountOfStolenCameras() {
        StolenCameraDatabase database = new StolenCameraDatabaseImpl();

        assertEquals(0, database.getStolenCameraCount());

        StolenCamera camera1 = new StolenCamera("123", new Date(1));
        database.addStolenCamera(camera1);
        assertEquals(1, database.getStolenCameraCount());

        StolenCamera camera2 = new StolenCamera("123", new Date(2));
        database.addStolenCamera(camera2);
        assertEquals("Same serial number added twice, should be considered same camera", 1, database
                .getStolenCameraCount());

        StolenCamera camera3 = new StolenCamera("321", new Date(1));
        database.addStolenCamera(camera3);
        assertEquals(2, database.getStolenCameraCount());
    }

    @Test
    public void testDatabaseRejectsRequestsToAddCameraWithSameSerialAsOneInDatabase() {
        StolenCameraDatabase database = new StolenCameraDatabaseImpl();

        StolenCamera camera1 = new StolenCamera("123", new Date(1));
        database.addStolenCamera(camera1);

        StolenCamera camera2 = new StolenCamera("123", new Date(3));
        database.addStolenCamera(camera2);

        assertTrue(database.wasPhotoTakenWhilstStolen("123", new Date(2)));
    }

}
