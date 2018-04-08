package seedu.address.model.person.timetable;

//@@author marlenekoh

import java.util.ArrayList;

/**
 * A class containing utility methods for comparing two timetables
 */
public class TimetableComparatorUtil {

    public static void compareTimetable(Timetable first, String second) {
        compareTimetable(first, new Timetable(second));
    }

    public static void compareTimetable(Timetable first, Timetable second) {
        ArrayList<TimetableModuleSlot> allUnsortedModulesSlots = new ArrayList<TimetableModuleSlot>();
        allUnsortedModulesSlots.addAll(first.getAllModulesSlots());
        allUnsortedModulesSlots.addAll(second.getAllModulesSlots());
        TimetableParserUtil.sortModuleSlotsByDay(allUnsortedModulesSlots);
    }
}
