package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.Assert.assertDoesNotThrow;
import static seedu.address.testutil.TestUtil.getFilePathInSandboxFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.util.SampleDataUtil;

//@@author marlenekoh
public class FileTimetableStorageTest {

    private static final String TEST_PATH = getFilePathInSandboxFolder("testReadingWriting");
    private static final String EXPECTED_TIMETABLE_PAGE_HTML_PATH
            = getFilePathInSandboxFolder("expectedTimetablePage.html");
    private static final String TIMETABLE_PAGE_HTML_PATH = getFilePathInSandboxFolder("TimetablePage.html");
    private static final String TIMETABLE_PAGE_CSS_PATH = getFilePathInSandboxFolder("TimetableStyle.css");
    private static final String TIMETABLE_DISPLAY_INFO_FILE_PATH
            = getFilePathInSandboxFolder("timetableDisplayInfo");
    private static String timetableDisplayInfoContents;
    private FileTimetableStorage fileTimetableStorage;

    @Before
    public void initialize() throws IOException {
        fileTimetableStorage = new FileTimetableStorage(TIMETABLE_PAGE_HTML_PATH, TIMETABLE_PAGE_CSS_PATH,
                TIMETABLE_DISPLAY_INFO_FILE_PATH);
        File file1 = new File(TIMETABLE_PAGE_HTML_PATH);
        File file2 = new File(TIMETABLE_PAGE_CSS_PATH);
        File file3 = new File(TIMETABLE_DISPLAY_INFO_FILE_PATH);

        PrintWriter writer = new PrintWriter(file1);
        writer.print("");
        writer.close();

        writer = new PrintWriter(file2);
        writer.print("");
        writer.close();

        writer = new PrintWriter(file3);
        writer.print("");
        writer.close();

        File expectedTimetableDisplayInfo = new File(
                getFilePathInSandboxFolder("expectedTimetableDisplayInfoView"));
        timetableDisplayInfoContents = FileUtil.readFromFile(expectedTimetableDisplayInfo);
    }

    @Test
    public void setUpTimetableDisplayFiles() throws IOException {
        fileTimetableStorage.setUpTimetableDisplayFiles(timetableDisplayInfoContents);
        assertEquals(FileUtil.readFromFile(new File(EXPECTED_TIMETABLE_PAGE_HTML_PATH)),
                FileUtil.readFromFile(new File(TIMETABLE_PAGE_HTML_PATH)));
        assertEquals(timetableDisplayInfoContents, FileUtil.readFromFile(new File(TIMETABLE_DISPLAY_INFO_FILE_PATH)));
        assertEquals(SampleDataUtil.getDefaultTimetablePageCss(), FileUtil.readFromFile(new File(TIMETABLE_PAGE_CSS_PATH)));
    }

    @Test
    public void writeToFile() throws IOException {
        String toWrite = "hello 123\n";
        fileTimetableStorage.writeToFile(toWrite, TEST_PATH);
        File testFile = new File(TEST_PATH);
        assertDoesNotThrow(() -> FileUtil.readFromFile(testFile));
        assertEquals(toWrite, FileUtil.readFromFile(testFile));
    }

    @Test
    public void getFileContents() throws FileNotFoundException {
        String expected = "hello 123\n";
        String actual = fileTimetableStorage.getFileContents(TEST_PATH);
        assertDoesNotThrow(() -> fileTimetableStorage.getFileContents(TEST_PATH));
        assertEquals(expected, actual);
    }

    @Test
    public void replaceLineExcludingStartEnd() {
        String contents = "This is a story about the weather.\n"
                + "Today it rained and the ground became muddy.\n"
                + "After the rain stopped, the sun came out.\n";
        String replace = "Yesterday it didn't rain at all and";
        String start = "Today";
        String end = ",";

        String expected = "This is a story about the weather.\n"
                + "Yesterday it didn't rain at all and the sun came out.\n";
        String result = fileTimetableStorage.replaceLineExcludingStartEnd(contents, replace, start, end);
        assertEquals(expected, result);
    }
}
