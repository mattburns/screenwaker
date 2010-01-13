package uk.co.mattburns.stolencamerafinder.scrapers.flickr;

import java.util.ArrayList;
import java.util.List;

import uk.co.mattburns.stolencamerafinder.StolenCamera;
import uk.co.mattburns.stolencamerafinder.StolenCameraDatabase;
import uk.co.mattburns.stolencamerafinder.StolenCameraDatabaseImpl;
import uk.co.mattburns.stolencamerafinder.util.FlickrFactory;
import uk.co.mattburns.stolencamerafinder.util.FlickrPhotos;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;

/**
 * Create a default database of stolen cameras from flickr
 * 
 * @author matt
 * 
 */
public class StolenCameraDatabaseFactory {
    private final static StolenCameraDatabase database = new StolenCameraDatabaseImpl();
    public static final String GROUP_ID = "1108839@N25"; //SCF
    //public static final String GROUP_ID = "812770@N20"; //RA
    public static final String GROUP_URL = "http://www.flickr.com/groups/stolencamerafinder/";

    public static StolenCameraDatabase createDatabase() {
        return createDatabase(GROUP_ID);
    }

    protected static StolenCameraDatabase createDatabase(String groupID) {
        Flickr flickr = FlickrFactory.getFlickr();
        PhotoList photos = null;
        try {
            photos = flickr.getPoolsInterface().getPhotos(groupID, null, 0, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> users = new ArrayList<String>();
        for (Object photoObject : photos) {
            Photo photo = (Photo) photoObject;
            String userid = photo.getOwner().getId();

            // only add 1 photo per userid
            if (!users.contains(userid)) {
                users.add(userid);
                String serial = FlickrPhotos.getSerialFromPhoto(photo);
                if (serial != null) {
                    database.addStolenCamera(new StolenCamera(photo.getId()));
                }
            }
        }

        return database;
    }
}
