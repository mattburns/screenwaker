package uk.co.mattburns.stolencamerafinder.util;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.photos.Exif;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photos.PhotosInterface;

public class ExifTagListerTest {
    private static final Logger log = Logger.getLogger(ExifTagListerTest.class);

    /**
     * This simply fetches exif data for a short amount of time and prints what
     * it found. I mainly used this to see what exif tags were available on
     * flickr
     * 
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    @Test
    @Ignore("Slow and pointless")
    public void testExifTagLister() throws IOException, SAXException, FlickrException {
        Flickr flickr = FlickrFactory.getFlickr();
        ExifTagLister tagLister = new ExifTagLister();
        Map<String, Set<String>> tagList = tagLister.getTagList(flickr, 1);
        log.info(tagList);
    }

    /**
     * This just grabs a few photos from flickr and checks that it doesn't have
     * any exif tag we don't recognise. This test is not deterministic (which
     * goes against every bone in my body) but unless we able to read ALL exif
     * stored on flickr, we can never make it deterministic, so we may as well
     * make it quick. Any time it fails, it will at least print the tags it
     * found so they can be added to the list of known tags.
     * 
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    @Test
    public void testNoUnknownTagsExist() throws IOException, SAXException, FlickrException {
        final int photosToSearch = 1;
        TreeMap<String, String> unknownTags = new TreeMap<String, String>();
        Flickr flickr = FlickrFactory.getFlickr();
        ExifTagLister tagLister = new ExifTagLister();
        PhotosInterface pi = flickr.getPhotosInterface();
        PhotoList photos = pi.getRecent(photosToSearch, 0);
        int currentPhoto = 0;
        for (Object photoObject : photos) {
            currentPhoto++;
            // log percentage done...
            log.info((int) (((float) currentPhoto / photosToSearch) * 100) + "%");
            Photo photo = (Photo) photoObject;
            Collection<Exif> exifObjects = tagLister.getExif(photo, pi);
            for (Exif exif : exifObjects) {
                String tag = exif.getTag();
                if (!SerialNumbers.isKnownTag(tag)) {
                    unknownTags.put(tag, exif.getLabel());
                }
            }
        }

        if (!unknownTags.isEmpty()) {
            // This just helps me paste them into the code
            log
                    .warn("New tags, previously unknown, have been found. Ensure there are no new serial number tags, then add the following exif tags to the properties file: ");
            for (String tag : unknownTags.keySet()) {
                System.out.println(tag + "=" + unknownTags.get(tag));
            }
        }
        assertTrue(unknownTags.isEmpty());
    }
}
