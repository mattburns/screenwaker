package uk.co.mattburns.stolencamerafinder.util;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;

import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.photos.Exif;
import com.aetrion.flickr.photos.Photo;

public class FlickrPhotos {

    /**
     * Get the serial number from a photo
     * 
     * @param photo
     * @return String serial number or null if there is none in this photo
     */
    @SuppressWarnings("unchecked")
    public static String getSerialFromPhoto(Photo photo) {
        String serial = null;
        Collection<Exif> exifs = new HashSet<Exif>();
        try {
            exifs = FlickrFactory.getPhotosInterface().getExif(photo.getId(), FlickrFactory.SECRET);
        } catch (Exception e) {
        }
        for (Exif exif : exifs) {
            if (SerialNumbers.getSerialTags().contains(exif.getTag())) {
                serial = exif.getRaw();
                if (serial != null) {
                    return serial;
                }
            }
        }
        return null;
    }

    public static void reportStolenCameraInComments(Photo photoFound, Photo photoReported) {
        try {
            RequestContext rc = RequestContext.getRequestContext();
            FlickrFactory.getFlickr().setSharedSecret(FlickrFactory.SECRET);
            String frob = FlickrFactory.getFlickr().getAuthInterface().getFrob();
            URL auth = FlickrFactory.getFlickr().getAuthInterface().buildAuthenticationUrl(Permission.WRITE, frob);

            Auth ath = new Auth();
            rc.setAuth(ath);
            ath.setPermission(Permission.WRITE);

            FlickrFactory.getFlickr().getAuthInterface().getToken(frob);
            FlickrFactory.getFlickr().getCommentsInterface().addComment(photoReported.getId(),
                    "found " + photoFound.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
