package seedu.address.model.person.timetable;

/**
 * Represents a module from NUSMods timetable.
 */
public class TimetableModule {
    private final String moduleCode;
    private final String lessonType;
    private final String classType;

    public TimetableModule(String moduleCode, String lessonType, String classNumber) {
        this.moduleCode = moduleCode;
        this.lessonType = lessonType;
        this.classType = classNumber;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getClassType() {
        return classType;
    }
}
