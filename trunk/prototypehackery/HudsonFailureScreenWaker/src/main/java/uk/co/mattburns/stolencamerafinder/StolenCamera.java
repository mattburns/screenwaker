package uk.co.mattburns.stolencamerafinder;

import java.util.Date;

import uk.co.mattburns.stolencamerafinder.util.FlickrFactory;
import uk.co.mattburns.stolencamerafinder.util.FlickrPhotos;

import com.aetrion.flickr.photos.Photo;

/**
 * Representation of a stolen camera
 * 
 * @author matt
 * 
 */
public class StolenCamera {

    private final String serialNumber;
    private final Date stolenDate;
    private final String photoID;

    /**
     * Create a stolen camera based on the data that can be extracted from a flickr photo
     * 
     * @param photoID
     */
    public StolenCamera(String photoID) {
        this.photoID = photoID;
        Photo photo = FlickrFactory.getPhoto(photoID);
        this.serialNumber = FlickrPhotos.getSerialFromPhoto(photo);
        this.stolenDate = photo.getDateTaken();
    }

    public StolenCamera(String serialNumber, Date stolenDate) {
        this.serialNumber = serialNumber;
        this.stolenDate = stolenDate;
        this.photoID = null;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Date getStolenDate() {
        return stolenDate;
    }

    public String getPhotoID() {
        return photoID;
    }
}
