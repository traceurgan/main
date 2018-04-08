package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author traceurgan
/**
 * An event requesting to view the Journal Window.
 */
public class ShowJournalWindowRequestEvent extends BaseEvent {

    public final String date;

    public ShowJournalWindowRequestEvent (String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
