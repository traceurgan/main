package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.journalEntry.JournalEntry;

/** Indicates a journal entry should be created/updated*/
public class SaveEntryEvent extends BaseEvent {

    public final JournalEntry journalEntry;

    public SaveEntryEvent(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
    }

    @Override
    public String toString() {
        return "Saving entry...";
    }
}
