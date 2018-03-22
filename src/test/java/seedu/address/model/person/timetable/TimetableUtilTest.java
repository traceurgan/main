package seedu.address.model.person.timetable;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

public class TimetableUtilTest {

    @Test
    public void expandShortTimetableUrl_invalidShortUrl_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.expandShortTimetableUrl("")); // empty string
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.expandShortTimetableUrl("www.google.com")); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.expandShortTimetableUrl("http://www.facebook.com")); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.expandShortTimetableUrl("http://www.modsn.us/")); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.expandShortTimetableUrl("http://www.modsn.us/q7cLP")); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.expandShortTimetableUrl("http://www.modsn.us/")); // code-part needs at least 1 character
    }

    @Test
    public void expandShortTimetableUrl_validUrl() throws ParseException {
        String actualResult = TimetableUtil.expandShortTimetableUrl("http://modsn.us/wNuIW");
        String expectedResult = "https://nusmods.com/timetable/sem-2/"
                + "share?CS2101=SEC:C01&CS2103T=TUT:C01&CS3230=LEC:1,TUT:4&"
                + "CS3241=LAB:3,LEC:1,TUT:3&CS3247=LAB:1,LEC:1&GES1021=LEC:SL1";
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void expandShortTimetableUrl_invalidUrl_throwsParseException() {
        Assert.assertThrows(ParseException.class, () ->
                TimetableUtil.expandShortTimetableUrl("http://modsn.us/wNuIW1"));
        Assert.assertThrows(ParseException.class, () ->
                TimetableUtil.expandShortTimetableUrl("http://modsn.us/123"));
    }

    @Test
    public void splitLongTimetableUrl () {
        ArrayList<TimetableModule> expectedListOfModules = new ArrayList<TimetableModule>();
        ArrayList<LessonPair> tempLessonPair;

        tempLessonPair = new ArrayList<LessonPair>();
        tempLessonPair.add(new LessonPair("SEC", "C01"));
        expectedListOfModules.add(new TimetableModule("CS2101",
                tempLessonPair));

        tempLessonPair = new ArrayList<LessonPair>();
        tempLessonPair.add(new LessonPair("TUT", "C01"));
        expectedListOfModules.add(new TimetableModule("CS2103T",
                tempLessonPair));

        tempLessonPair = new ArrayList<LessonPair>();
        tempLessonPair.add(new LessonPair("LEC", "1"));
        tempLessonPair.add(new LessonPair("TUT", "4"));
        expectedListOfModules.add(new TimetableModule("CS3230",
                tempLessonPair));

        tempLessonPair = new ArrayList<LessonPair>();
        tempLessonPair.add(new LessonPair("LAB", "3"));
        tempLessonPair.add(new LessonPair("LEC", "1"));
        tempLessonPair.add(new LessonPair("TUT", "3"));
        expectedListOfModules.add(new TimetableModule("CS3241",
                tempLessonPair));

        tempLessonPair = new ArrayList<LessonPair>();
        tempLessonPair.add(new LessonPair("LAB", "1"));
        tempLessonPair.add(new LessonPair("LEC", "1"));
        expectedListOfModules.add(new TimetableModule("CS3247",
                tempLessonPair));

        tempLessonPair = new ArrayList<LessonPair>();
        tempLessonPair.add(new LessonPair("LEC", "SL1"));
        expectedListOfModules.add(new TimetableModule("GES1021",
                tempLessonPair));

        String longUrl = "https://nusmods.com/timetable/sem-2/"
                + "share?CS2101=SEC:C01&CS2103T=TUT:C01&CS3230=LEC:1,TUT:4&"
                + "CS3241=LAB:3,LEC:1,TUT:3&CS3247=LAB:1,LEC:1&GES1021=LEC:SL1";
        ArrayList<TimetableModule> actualListOfModules = TimetableUtil.splitLongTimetableUrl(longUrl);
        assertEquals(expectedListOfModules, actualListOfModules);
    }
}
