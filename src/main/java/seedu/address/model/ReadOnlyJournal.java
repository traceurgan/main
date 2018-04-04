package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.journalentry.JournalEntry;

/**
 * Unmodifiable view of an journal
 */
public interface ReadOnlyJournal {

    /**
     * Returns an unmodifiable view of the journal entry list.
     * This list will not contain any duplicate journal entries.
     */
    ObservableList<JournalEntry> getJournalEntryList();

    JournalEntry getLast();
}
