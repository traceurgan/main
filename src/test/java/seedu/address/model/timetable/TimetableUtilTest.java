package seedu.address.model.timetable;

import org.junit.Test;

import seedu.address.model.person.timetable.TimetableUtil;

public class TimetableUtilTest {
    @Test
    public void expandShortTimetableUrl_invalidUrl() throws Exception {
        TimetableUtil.expandShortTimetableUrl("www.google.com");
    }
}
