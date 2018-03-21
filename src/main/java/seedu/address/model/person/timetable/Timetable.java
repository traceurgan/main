package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

/**
 * Represents the NUSMODS timetable of the partner
 */
public class Timetable {
    public static final String MESSAGE_TIMETABLE_CONSTRAINTS = "Short NUSMods timetable URL should be of the format "
            + "http://modsn.us/code-part "
            + "and adhere to the following constraints:\n"
            + "1. The URL should start with http://modsn.us/\n"
            + "2. The code-part should only contain alphanumeric characters.";

    private static final String SHORT_NUSMODS_URL_REGEX = "http://modsn.us/";
    private static final String CODE_PART_REGEX = "[\\w]+";
    public static final String TIMETABLE_VALIDATION_REGEX = SHORT_NUSMODS_URL_REGEX + CODE_PART_REGEX;

    public final String value;

    private int currentSemester;
    private ArrayList<TimetableDayInfo> listOfDays;

    private ArrayList<TimetableModule> listOfModules;
    private String expandedUrl;

    public Timetable(String timetableUrl) {
        requireNonNull(timetableUrl);
        this.value = timetableUrl;
        //TimetableUtil.setUpTimetableInfo(this);
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public void setListOfModules(ArrayList<TimetableModule> listOfModules) {
        this.listOfModules = listOfModules;
    }

    /**
     * Returns if a given string is a valid short NUSMods timetable URL.
     */
    public static boolean isValidTimetable(String test) {
        return test.matches(TIMETABLE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
