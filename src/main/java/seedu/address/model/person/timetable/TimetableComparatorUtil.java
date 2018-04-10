package seedu.address.model.person.timetable;

//@@author marlenekoh

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class containing utility methods for comparing two timetables
 */
public class TimetableComparatorUtil {

    /**
     * Overloads compareTimetable method.
     * @param first Timetable to compare with
     * @param second String containing valid short NUSMods URL
     */
    public static void compareTimetable(Timetable first, String second) {
        compareTimetable(first, new Timetable(second));
    }

    /**
     * Set ups files for comparing.
     * @param first Timetable to compare
     * @param second Timetable to compare
     */
    public static void compareTimetable(Timetable first, Timetable second) {
        ArrayList<TimetableModuleSlot> allUnsortedModulesSlots =
                TimetableDisplayUtil.setUpUnsortedModuleSlotsForComparing(first, second);
        HashMap<String, ArrayList<TimetableModuleSlot>> sortedModuleSlots =
                TimetableParserUtil.sortModuleSlotsByDay(allUnsortedModulesSlots);

        TimetableDisplayUtil.setUpTimetableDisplayInfo(sortedModuleSlots);
        TimetableDisplayUtil.setUpTimetablePageScriptFile();
    }

}
