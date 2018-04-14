package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.journalentry.Date;

/**
 * An event requesting to view a journal entry.
 */
public class ViewJournalEntryRequestEvent extends BaseEvent {


    public final Date date;

    public ViewJournalEntryRequestEvent (Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
