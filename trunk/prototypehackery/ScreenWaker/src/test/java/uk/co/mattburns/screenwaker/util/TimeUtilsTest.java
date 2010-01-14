package uk.co.mattburns.screenwaker.util;

import javax.swing.JTextField;

import org.junit.Test;
import static org.junit.Assert.*;


public class TimeUtilsTest {

    @Test
    public void testZeroTime() {
        assertEquals(0, TimeUtils.hoursMinutesSecondsToMillis(0, 0 ,0));
    }
    
    @Test
    public void testSeconds() {
        assertEquals(1000, TimeUtils.hoursMinutesSecondsToMillis(0, 0 ,1));
    }
    
    @Test
    public void testMinutes() {
        assertEquals(60000, TimeUtils.hoursMinutesSecondsToMillis(0, 1 ,0));
    }
    
    @Test
    public void testHours() {
        assertEquals(3600000, TimeUtils.hoursMinutesSecondsToMillis(1, 0 ,0));
    }
    
    @Test
    public void testHoursMinutesAndSeconds() {
        assertEquals(3661000, TimeUtils.hoursMinutesSecondsToMillis(1, 1, 1));
    }
    
    @Test
    public void testTextFieldsBehaveSameAsInts() {
        JTextField one = new JTextField("1");
        JTextField two = new JTextField("2");
        JTextField three = new JTextField("3");
        assertEquals(TimeUtils.hoursMinutesSecondsToMillis(1, 2, 3),
                TimeUtils.timeFieldsToMillis(one, two, three));
    }
}
