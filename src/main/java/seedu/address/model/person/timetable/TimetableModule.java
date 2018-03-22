package seedu.address.model.person.timetable;

import java.util.ArrayList;

/**
 * Represents a module from NUSMods timetable.
 */
public class TimetableModule {
    private final String moduleCode;
    private ArrayList<LessonPair> listOfLessons;

    public TimetableModule(String moduleCode, ArrayList<LessonPair> listOfLessons) {
        this.moduleCode = moduleCode;
        this.listOfLessons = listOfLessons;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public ArrayList<LessonPair> getListOfLessons() {
        return listOfLessons;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this // short circuit if same object
                || (other instanceof TimetableModule // instanceof handles nulls
                && this.moduleCode.equals(((TimetableModule) other).moduleCode))) { // state check
            for (int i = 0; i < listOfLessons.size(); i++) {
                if (!listOfLessons.get(i).equals(((TimetableModule) other).listOfLessons.get(i)))
                    return false;
            }
        }
        return true;
    }
}
