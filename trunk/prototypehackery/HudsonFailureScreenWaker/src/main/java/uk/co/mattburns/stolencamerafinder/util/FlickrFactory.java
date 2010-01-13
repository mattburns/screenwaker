package uk.co.mattburns.stolencamerafinder.util;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotosInterface;

public class FlickrFactory {

    private static Flickr flickr;
    public static String SECRET = "d499fd14c2642efc";
    public static String API_KEY = "a66c6b852c9a1c1d8591acab35de6830";

    public static Flickr getFlickr() {
        if (flickr != null) {
            return flickr;
        } else {
            try {
                flickr = new Flickr(API_KEY, SECRET, new REST("api.flickr.com"));
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
            return flickr;
        }
    }

    public static PhotosInterface getPhotosInterface() {
        return getFlickr().getPhotosInterface();
    }

    public static Photo getPhoto(String photoID) {
        Photo photo = null;
        try {
            photo = getPhotosInterface().getPhoto(photoID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photo;
    }
}
