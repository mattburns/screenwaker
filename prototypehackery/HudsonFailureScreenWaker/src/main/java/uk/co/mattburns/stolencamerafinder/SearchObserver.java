package uk.co.mattburns.stolencamerafinder;

import com.aetrion.flickr.photos.Photo;

/**
 * Implementations of this object are to be passed into searches as an observer
 * 
 * @author matt
 */
public interface SearchObserver {

    /**
     * Return true when you want the search to stop
     * 
     * @return true
     */
    boolean shouldAbort();

    /**
     * This method will be invoked when a photo is found
     * 
     * @param photo
     */
    void reportPhotoFound(Photo photo);

    /**
     * This method will be invoked when a photo is searched
     * 
     * @param photo
     */
    void reportPhotoSearched(Photo photo);
}
