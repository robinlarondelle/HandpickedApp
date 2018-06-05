package com.example.jan_paul.handpickedandroidclient.Domain;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by tobia on 4-6-2018.
 */
public class TimeRangeTest {
    @Test
    public void isInRange_noExceptionWithBeginAndEndIsNull() throws Exception {
        boolean expected = true;
        boolean actual;

        Calendar begin = null;
        Calendar end = null;

        TimeRange timeRange = new TimeRange(begin, end);

        actual = timeRange.isInRange();

        assertEquals("Check begin and end Calendar variables", expected, actual);
    }

    @Test
    public void isInRange_noExceptionAfterBeginAndBeforeEnd() throws Exception {
        boolean expected = true;
        boolean actual;

        Calendar begin = Calendar.getInstance();
        begin.add(Calendar.SECOND, -5);

        Calendar end = Calendar.getInstance();
        end.add(Calendar.SECOND, 5);

        TimeRange timeRange = new TimeRange(begin, end);

        actual = timeRange.isInRange();

        assertEquals("Check begin and end Calendar variables", expected, actual);
    }

    @Test
    public void isInRange_wrongBeginAndEndCalendar() throws Exception {
        boolean expected = false;
        boolean actual;

        Calendar begin = Calendar.getInstance();
        begin.add(Calendar.SECOND, 5);

        Calendar end = Calendar.getInstance();
        end.add(Calendar.SECOND, -5);

        TimeRange timeRange = new TimeRange(begin, end);

        actual = timeRange.isInRange();

        assertEquals("Check begin and end Calendar variables", expected, actual);
    }
}