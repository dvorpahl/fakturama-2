package com.sebulli.fakturama.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

class DateFormatterTest {
    //
    //    @Test
    //     void testDateAndTimeAsLocalString() {
    //        IDateFormatterService formatter = new DateFormatter();
    //        assertEquals("Date is invalid!", "Mittwoch, 20. Mai 2020 18:57:00", formatter.DateAndTimeAsLocalString("2020-05-20 18:57:00"));
    //
    //    }
    //
    //    @Test
    //     void testDateAsISO8601StringString() {
    //        fail("Not yet implemented");
    //    }
    //
    //    @Test
    //     void testDateAsISO8601String() {
    //        fail("Not yet implemented");
    //    }
    //
    //    @Test
    //     void testDateAndTimeOfNowAsISO8601String() {
    //        fail("Not yet implemented");
    //    }
    //
    //    @Test
    //     void testGetDateAndTimeAsString() {
    //        fail("Not yet implemented");
    //    }
    //
    //    @Test
    //     void testGetDateTimeAsStringCalendar() {
    //        fail("Not yet implemented");
    //    }
    //
    //    @Test
    //     void testGetDateTimeAsLocalString() {
    //        fail("Not yet implemented");
    //    }
    //
    //    @Test
    //     void testGetFormattedLocalizedDate() {
    //        fail("Not yet implemented");
    //    }

    @Test
    void testGetCalendarFromDateString() {
        IDateFormatterService formatter = new DateFormatter();
        Calendar testCal = Calendar.getInstance();
        testCal.clear();
        testCal.set(2020, 1, 20, 0, 0, 0);

        assertEquals(testCal, formatter.getCalendarFromDateString("2020-02-20"));
        assertEquals(testCal, formatter.getCalendarFromDateString("20.02.2020"));
        assertEquals(testCal, formatter.getCalendarFromDateString("02/20/2020"));
    }

    //    @Test
    //     void testDateAndTimeOfNowAsLocalString() {
    //        fail("Not yet implemented");
    //    }
    //
    //    @Test
    //     void testGetDateTimeAsStringDateTime() {
    //        fail("Not yet implemented");
    //    }
    //
}
