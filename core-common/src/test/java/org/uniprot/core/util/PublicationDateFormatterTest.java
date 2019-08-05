package org.uniprot.core.util;

import org.junit.jupiter.api.Test;
import org.uniprot.core.util.PublicationDateFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created 09/07/19
 *
 * @author Edd
 */
class PublicationDateFormatterTest {
    @Test
    void test_yyyy_ConvertStringToDate() {
        try {
            Date date = PublicationDateFormatter.YEAR.convertStringToDate("2006");
            verifyDate(date, 2006, -1, -1);

        } catch (Exception e) {
            e.printStackTrace();
            fail("YEAR failed");
        }
    }

    @Test
    void test_YEAR_DIGIT_MONTH_ConvertStringToDate() {
        try {
            Date date = PublicationDateFormatter.YEAR_DIGIT_MONTH.convertStringToDate("2005-10");
            verifyDate(date, 2005, 9, -1);

        } catch (Exception e) {
            fail("YEAR_DIGIT_MONTH failed");
        }
    }

    @Test
    void test_THREE_LETTER_MONTH_YEAR_ConvertStringToDate() {
        try {
            Date date = PublicationDateFormatter.THREE_LETTER_MONTH_YEAR.convertStringToDate("MAR-2002");
            verifyDate(date, 2002, 2, -1);

        } catch (Exception e) {
            fail("THREE_LETTER_MONTH_YEAR failed");
        }
    }

    @Test
    void test_DAY_DIGITMONTH_YEAR_ConvertStringToDate() {
        try {
            Date date = PublicationDateFormatter.DAY_DIGITMONTH_YEAR.convertStringToDate("2001-11-20");
            verifyDate(date, 2001, 10, 20);

        } catch (Exception e) {
            fail("DAY_DIGITMONTH_YEAR failed");
        }
    }

    @Test
    void test_DAY_THREE_LETTER_MONTH_YEAR_ConvertStringToDate() {
        try {
            Date date = PublicationDateFormatter.DAY_THREE_LETTER_MONTH_YEAR.convertStringToDate("10-MAY-2001");
            verifyDate(date, 2001, 4, 10);

        } catch (Exception e) {
            fail("Year test failed");
        }
    }

    private void verifyDate(Date date, int expectedYear, int expectedMonth, int expectedDay) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        assertEquals(expectedYear, calendar.get(Calendar.YEAR));
        if (expectedMonth != -1) {
            assertEquals(expectedMonth, calendar.get(Calendar.MONTH));
        }
        if (expectedDay != -1) {
            assertEquals(expectedDay, calendar.get(Calendar.DAY_OF_MONTH));
        }
    }
}