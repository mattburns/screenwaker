package uk.co.mattburns.stolencamerafinder;

import java.util.Date;

import uk.co.mattburns.stolencamerafinder.util.FlickrFactory;
import uk.co.mattburns.stolencamerafinder.util.FlickrPhotos;

import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.SearchParameters;

public class StolenCameraFinderImpl implements StolenCameraFinder {

    private static final int MAX_SEARCH = 500;

    private StolenCameraDatabase stolenCameraDatabase;

    @Override
    public void findPhoto(String[] photoTags, TAG_SEARCH_MODE tagMode, SearchObserver controller) {
        findPhoto(photoTags, tagMode, DEFAULT_MIN_DATE, DEFAULT_MAX_DATE, controller);
    }

    @Override
    public void findPhoto(String[] photoTags, TAG_SEARCH_MODE tagMode, Date minTakenDate, Date maxTakenDate,
            SearchObserver controller) {
        PhotosInterface photosInterface = FlickrFactory.getPhotosInterface();
        SearchParameters params = new SearchParameters();
        params.setTags(photoTags);
        params.setTagMode(tagMode.toString());
        params.setMinTakenDate(minTakenDate);
        params.setMaxTakenDate(maxTakenDate);
        PhotoList photos = new PhotoList();
        int page = 0;
        while (!controller.shouldAbort() && (page == 0 || photos.size() == MAX_SEARCH)) {
            try {
                photos = photosInterface.search(params, MAX_SEARCH, page);
            } catch (Exception e) {
            }
            for (Object photoObject : photos) {
                Photo photo = (Photo) photoObject;
                if (photoIsKnownStolenCamera(photo)) {
                    controller.reportPhotoFound(photo);
                }
                controller.reportPhotoSearched(photo);
                if (controller.shouldAbort()) {
                    break;
                }
            }
            page++;
        }
    }

    /**
     * Return true if the given photo was taken with a camera with the given serial
     * 
     * @param photo
     * @param cameraSerialNumber
     * @return
     */
    public boolean photoIsKnownStolenCamera(Photo photo) {
        String serial = FlickrPhotos.getSerialFromPhoto(photo);
        if (serial != null) {
            photo = FlickrFactory.getPhoto(photo.getId());
            Date dateTaken = photo.getDateTaken();
            return stolenCameraDatabase.wasPhotoTakenWhilstStolen(serial, dateTaken);
        } else {
            return false;
        }
    }

    public StolenCameraDatabase getStolenCameraDatabase() {
        return stolenCameraDatabase;
    }

    public void setStolenCameraDatabase(StolenCameraDatabase stolenCameraDatabase) {
        this.stolenCameraDatabase = stolenCameraDatabase;
    }

}
