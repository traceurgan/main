package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.journalEntry.JournalEntry;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyJournal {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<JournalEntry> getJournalEntryList();
}
