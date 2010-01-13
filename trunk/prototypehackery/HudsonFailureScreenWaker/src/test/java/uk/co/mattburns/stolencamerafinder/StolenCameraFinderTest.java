package uk.co.mattburns.stolencamerafinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import uk.co.mattburns.stolencamerafinder.StolenCameraFinder.TAG_SEARCH_MODE;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.photos.Photo;

public class StolenCameraFinderTest {

    /**
     * Test that given a set of tags and a serial number, we can find the photo. (the photo is one of mine, and I know
     * it exists) "http://flickr.com/photos/55198508@N00/3579700130"
     * 
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     * @throws InterruptedException
     */
    @Test
    @Ignore("Slow and pointless")
    public void testCameraIsFoundWithTags() throws IOException, SAXException, FlickrException, InterruptedException {
        String[] photoTags = new String[] { "westmill", "wind farm", "wind", "windmill" };
        String cameraSerialNumber = "1280853970";
        StolenCameraFinder scf = new StolenCameraFinderImpl();
        ResultListener listener = new ResultListener();
        scf.findPhoto(photoTags, TAG_SEARCH_MODE.all, listener);
        assertNotNull(listener.getPhotoFound());
        assertEquals("55198508@N00", listener.getPhotoFound().getOwner().getId());
    }

    /**
     * Dummy search controller that will hold the most recently found photo
     * 
     * @author matt
     * 
     */
    public class ResultListener implements SearchObserver {

        private Photo photoFound = null;

        @Override
        public void reportPhotoFound(Photo photo) {
            photoFound = photo;
        }

        public Photo getPhotoFound() {
            return photoFound;
        }

        @Override
        public void reportPhotoSearched(Photo photo) {

        }

        @Override
        public boolean shouldAbort() {
            return false;
        }

    }
}
