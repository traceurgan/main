package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
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
