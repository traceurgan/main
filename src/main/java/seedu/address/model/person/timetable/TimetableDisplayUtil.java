package seedu.address.model.person.timetable;

import static seedu.address.model.person.timetable.TimetableParserUtil.FRIDAY_INDEX;
import static seedu.address.model.person.timetable.TimetableParserUtil.MONDAY_INDEX;
import static seedu.address.model.person.timetable.TimetableParserUtil.THURSDAY_INDEX;
import static seedu.address.model.person.timetable.TimetableParserUtil.TUESDAY_INDEX;
import static seedu.address.model.person.timetable.TimetableParserUtil.WEDNESDAY_INDEX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

//@@author marlenekoh
/**
 * A class containing utility methods for displaying a Timetable in the browser panel
 */
public class TimetableDisplayUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    public static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static final String[] WEEKS = {"Odd Week", "Even Week", "Every Week"};
    public static final String[] TIMES = {
            "0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130",
            "1200", "1230", "1300", "1330", "1400", "1430", "1500", "1530",
            "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930",
            "2000", "2030", "2100", "2130", "2200", "2230", "2300", "2330"
    };

    public static final String TIMETABLE_INFO_FILE_PATH = "src/main/resources/timetableDisplayInfo";
    public static final String TIMETABLE_PAGE_JS_PATH = "src/main/resources/view/TimetablePageScript.js";

    /**
     * Writes Timetable information to a text file
     * @param timetable the timetable to convert into string and write
     */
    public static void writeToTimetableDisplayInfoFile(Timetable timetable) {
        File timetableDisplayInfo = new File(TIMETABLE_INFO_FILE_PATH);
        try {
            PrintWriter printWriter = new PrintWriter(timetableDisplayInfo);
            printWriter.write(convertTimetableToString(timetable));
            printWriter.close();
        } catch (FileNotFoundException e) {
            logger.warning("File not found");
        }
    }

    /**
     * Converts the {@code listOfDays} into a String object for parsing
     * @param timetable which contains schedule to convert into JSON object
     */
    public static String convertTimetableToString(Timetable timetable) {
        StringBuilder sb = new StringBuilder();

        HashMap<String, ArrayList<TimetableModuleSlot>> listOfDays = timetable.getListOfDays();
        ArrayList<TimetableModuleSlot> slotsForTheDay = null;
        for (int i = 0; i < DAYS.length; i++) {
            switch (i) {
            case 0:
                slotsForTheDay = listOfDays.get(DAYS[MONDAY_INDEX].toUpperCase());
                break;

            case 1:
                slotsForTheDay = listOfDays.get(DAYS[TUESDAY_INDEX].toUpperCase());
                break;

            case 2:
                slotsForTheDay = listOfDays.get(DAYS[WEDNESDAY_INDEX].toUpperCase());
                break;

            case 3:
                slotsForTheDay = listOfDays.get(DAYS[THURSDAY_INDEX].toUpperCase());
                break;
            case 4:
                slotsForTheDay = listOfDays.get(DAYS[FRIDAY_INDEX].toUpperCase());
                break;
            }

            sb.append("[");
            for (TimetableModuleSlot t : slotsForTheDay) {
                sb.append(t.toString());
            }
            sb.append("]");
        }
        return sb.toString();
    }

    /**
     * Gets file contents from {@code TIMETABLE_PAGE_JS_PATH}
     * @return String containing file contents if file is valid
     */
    public static String getTimetablePageScriptFileContents() {
        File timetablePageScript = new File(TIMETABLE_PAGE_JS_PATH);
        try {
            BufferedReader br = new BufferedReader(new FileReader(timetablePageScript));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            logger.warning("Exception in reading file");
        }
        return null;
    }

    /**
     * Replaces file contents of file at {@code TIMETABLE_PAGE_JS_PATH} with {@code contents}
     * @param contents the new contents of the file
     */
    public static void writeToTimetablePageScriptFile(String contents) {
        File timetablePageScript = new File(TIMETABLE_PAGE_JS_PATH);
        try {
            PrintWriter printWriter = new PrintWriter(timetablePageScript);
            printWriter.write(contents);
            printWriter.close();
        } catch (FileNotFoundException e) {
            logger.warning("File not found");
        }
    }

    /**
     * Replaces line containing {@code find} in the javascript file at {@code TIMETABLE_PAGE_JS_PATH} with
     * @param contents original content of the javascript file
     * @param find line to replace
     * @param replace new line
     * @return new content
     */
    public static String replaceTimetableArray(String contents, String find, String replace){
        return contents.replace(find, replace);
    }
}
