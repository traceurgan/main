package seedu.address.model.person.timetable;

/**
 * Represents the lesson type and class type of one module.
 */
public class LessonPair {

    private final String lessonType;
    private final String classType;

    public LessonPair(String lessonType, String classType) {
        this.lessonType = lessonType;
        this.classType = classType;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getClassType() {
        return classType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonPair // instanceof handles nulls
                && this.getClassType().equals(((LessonPair) other).getClassType())
                && this.getLessonType().equals(((LessonPair) other).getLessonType())); // state check
    }
}
