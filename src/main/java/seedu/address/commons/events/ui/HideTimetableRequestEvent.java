package seedu.address.commons.events.ui;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@@author marlenekoh
/**
 * An event requesting to view the partner's timetable.
 */
public class HideTimetableRequestEvent extends BaseEvent {
    public final int targetIndex;

    public HideTimetableRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
