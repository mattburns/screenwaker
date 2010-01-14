package uk.co.mattburns.screenwaker.util;

import javax.swing.JTextField;

public class TimeUtils {

    public static long hoursMinutesSecondsToMillis(int hours, int minutes, int seconds) {
        return (hours * 60 * 60 * 1000) + (minutes * 60 * 1000) + (seconds * 1000);
    }

    public static long timeFieldsToMillis(JTextField hours, JTextField minutes, JTextField seconds) {
        return hoursMinutesSecondsToMillis(getIntFromField(hours), getIntFromField(minutes), getIntFromField(seconds));
    }

    private static int getIntFromField(JTextField field) {
        String entry = field.getText().trim();
        int result = 0;
        try {
            result = Integer.parseInt(entry);
        } catch (Exception e) {
            // ignore
        }
        return result;
    }
}
