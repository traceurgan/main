package seedu.address.model.journalEntry;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniqueJournalEntryList implements Iterable <JournalEntry>{

    private final ObservableList<JournalEntry> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(JournalEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws Exception if the person to add is a duplicate of an existing person in the list.
     */
    public void add(JournalEntry toAdd) throws Exception {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new Exception();
        }
        internalList.add(toAdd);
    }

    public void setJournalEntries(List<JournalEntry> journalEntries) {
        //do something
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<JournalEntry> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<JournalEntry> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueJournalEntryList // instanceof handles nulls
                && this.internalList.equals(((UniqueJournalEntryList) other).internalList));
    }
}
