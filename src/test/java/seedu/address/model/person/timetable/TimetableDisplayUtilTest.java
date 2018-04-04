package seedu.address.model.person.timetable;

import static seedu.address.model.person.timetable.TimetableParserUtilTest.VALID_SHORT_URL;

import org.junit.Before;
import org.junit.Test;

//@@author marlenekoh
public class TimetableDisplayUtilTest {
    private Timetable timetable = new Timetable(VALID_SHORT_URL);
    private final String EXPECTED_FILE_CONTENTS_PATH = "src/test/data/sandbox/expectedTimetableDisplayInfo";

    @Before
    private void setUpExpectedFileContents() {
    }

    @Test
    public void writeToTimetableFile_assertsSameFileContents() {
        TimetableDisplayUtil.writeToTimetableDisplayInfoFile(timetable);
    }
}
