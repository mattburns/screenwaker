package uk.co.mattburns.stolencamerafinder.scrapers.flickr;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import uk.co.mattburns.stolencamerafinder.StolenCameraDatabase;

public class StolenCameraDatabaseFactoryTest {
    @Test
    @Ignore("Slow, bad test!")
    public void testDatabaseFactoryCollectsCameras() {
        StolenCameraDatabase database = StolenCameraDatabaseFactory.createDatabase();
        assertTrue(database.getStolenCameraCount() > 0);
    }
}
