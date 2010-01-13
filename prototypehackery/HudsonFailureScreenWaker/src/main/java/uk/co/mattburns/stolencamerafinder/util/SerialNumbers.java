package uk.co.mattburns.stolencamerafinder.util;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

public class SerialNumbers {

    public static SortedSet<String> serialTags;
    public static SortedSet<String> knownTags;

    public static SortedSet<String> getSerialTags() {
        if (serialTags == null) {
            serialTags = new TreeSet<String>();
            List<String> tags = Arrays.asList(new String[] { "SerialNumber", "InternalSerialNumber" });
            serialTags.addAll(tags);
        }
        return serialTags;
    }

    public static SortedSet<String> getKnownTags() {
        if (knownTags == null) {
            knownTags = new TreeSet<String>();
            ResourceBundle rb = ResourceBundle.getBundle("exiftags");
            knownTags.addAll(rb.keySet());
        }
        return knownTags;
    }

    public static boolean isSerialTag(String tag) {
        return (getSerialTags().contains(tag));
    }

    public static boolean isKnownTag(String tag) {
        return getKnownTags().contains(tag);
    }
}
