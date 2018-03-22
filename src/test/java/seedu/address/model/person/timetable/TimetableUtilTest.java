package seedu.address.model.person.timetable;

import org.junit.Test;

public class TimetableUtilTest {
    @Test
    public void expandShortTimetableUrl_invalidUrl() throws Exception {
        TimetableUtil.expandShortTimetableUrl("www.google.com");
    }
}
