package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.timetable.Timetable;

//@@author marlenekoh
/**
 * An event requesting to view the partner's timetable.
 */
public class ShowTimetableRequestEvent extends BaseEvent {
    public final Timetable timetable;

    public ShowTimetableRequestEvent(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
