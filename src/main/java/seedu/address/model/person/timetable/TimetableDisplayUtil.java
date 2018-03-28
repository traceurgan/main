package seedu.address.model.person.timetable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class containing utility methods for displaying a Timetable in the browser panel
 */
public class TimetableDisplayUtil {
    public static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static final String[] WEEKS = {"Odd Week", "Even Week", "Every Week"};
    public static final String[] TIMES = {
            "0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130",
            "1200", "1230", "1300", "1330", "1400", "1430", "1500", "1530",
            "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930",
            "2000", "2030", "2100", "2130", "2200", "2230", "2300", "2330"
    };

    /**
     * Converts the {@code listOfDays} into a JSON object for parsing
     * @param timetable which contains schedule to convert into JSON object
     */
    public void convertTimetableToJson(Timetable timetable) {
        HashMap<String, ArrayList<TimetableModuleSlot>> listOfDays = timetable.getListOfDays();
        ArrayList<TimetableModuleSlot> slotsForTheDay = null;
        for (int i = 0; i < DAYS.length; i++) {
            switch (i) {
            case 0:
                slotsForTheDay = listOfDays.get("MONDAY");
                break;

            case 1:
                slotsForTheDay = listOfDays.get("TUESDAY");
                break;

            case 2:
                slotsForTheDay = listOfDays.get("WEDNESDAY");
                break;

            case 3:
                slotsForTheDay = listOfDays.get("THURSDAY");
                break;
            case 4:
                slotsForTheDay = listOfDays.get("FRIDAY");
                break;
            }

            for (TimetableModuleSlot t : slotsForTheDay) {

            }
        }
    }
}
