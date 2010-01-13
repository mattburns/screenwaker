package uk.co.mattburns.stolencamerafinder.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.photos.Exif;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photos.PhotosInterface;
import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;

public class ExifTagLister {

    public static final int MAX_RESULTS = 10;

    private static final Logger log = Logger.getLogger(ExifTagLister.class);

    /**
     * Search through flickr photos for a set length of time and return a
     * Collection of the exif tags found
     * 
     * @param flickr
     *            flickrj flickr objects
     * @param secret
     *            API secret
     * @param secondsToSpendSearching
     *            length of time to search for
     * @return Map whose keys are the exif tags and the values are a list of
     *         labels that have been attached to that tag
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public Map<String, Set<String>> getTagList(Flickr flickr, int secondsToSpendSearching) throws IOException,
            SAXException, FlickrException {

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (secondsToSpendSearching * 1000);
        int photosSearched = 0;
        Map<String, Set<String>> tagList = new TreeMap<String, Set<String>>();

        PhotosInterface pi = flickr.getPhotosInterface();

        for (int page = 0; System.currentTimeMillis() < endTime; page++) {
            PhotoList photos;
            photos = pi.getRecent(MAX_RESULTS, page);

            for (Object photoObject : photos) {
                if (System.currentTimeMillis() > endTime) {
                    break;
                }
                Photo photo = (Photo) photoObject;
                Collection<Exif> exifObjects = getExif(photo, pi);
                appendExifDataToTagList(tagList, exifObjects);
                photosSearched++;
            }
        }

        log.info("Photos searched " + photosSearched);

        return tagList;
    }

    @SuppressWarnings("unchecked")
    public Collection<Exif> getExif(Photo photo, PhotosInterface pi) {
        Collection<Exif> exifObjects = null;
        try {
            exifObjects = pi.getExif(photo.getId(), FlickrFactory.SECRET);
        } catch (MalformedByteSequenceException e) {
            // ignore. TODO: investigate!
            log.warn(e.getMessage());
        } catch (FlickrException e) {
            if (e.getErrorCode().equals("1")) {
                // photo not found, ignore
                log.warn(e.getMessage());
            } else if (e.getErrorCode().equals("2")) {
                // access denied, ignore
                log.warn(e.getMessage());
            } else {
                log.error(e);
            }
        } catch (IOException e) {
            log.error(e);
        } catch (SAXException e) {
            log.error(e);
            throw new RuntimeException();
        }
        if (exifObjects == null) {
            exifObjects = new ArrayList<Exif>();
        }
        return exifObjects;
    }

    private void appendExifDataToTagList(Map<String, Set<String>> tagList, Collection<Exif> exifObjects) {
        for (Exif exif : exifObjects) {
            String tag = exif.getTag();
            String label = exif.getLabel();

            if (!tagList.containsKey(tag)) {
                Set<String> labels = new HashSet<String>();
                tagList.put(tag, labels);
            }
            Set<String> labels = tagList.get(tag);
            labels.add(label);
        }
    }
}
