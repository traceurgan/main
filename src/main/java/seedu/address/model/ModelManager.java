package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.JournalChangedEvent;
import seedu.address.commons.events.model.PersonChangedEvent;
import seedu.address.model.journalentry.JournalEntry;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private Person person;
    private final Journal journal;
    private final ObservableList<ReadOnlyPerson> personAsList = FXCollections.observableArrayList();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyPerson person, ReadOnlyJournal journal, UserPrefs userPrefs) {
        super();
        requireAllNonNull(journal, userPrefs);

        logger.fine(
                "Initializing with partner: " + person + " , journal" + journal + " and user prefs " + userPrefs);

        this.person = new Person(person);
        this.journal = new Journal(journal);
    }

    public ModelManager() {
        this(null, new Journal(), new UserPrefs());
    }

    @Override
    public void resetJournalData(ReadOnlyJournal newData) {
        journal.resetJournalData(newData);
        indicateJournalChanged();
    }

    @Override
    public void resetPersonData(ReadOnlyPerson newData) {

    }

    @Override
    public ReadOnlyPerson getPerson() {
        return person;
    }

    @Override
    public ObservableList <ReadOnlyPerson> getPersonAsList() {
        personAsList.add(this.person);
        return personAsList;
    }

    /** Raises an event to indicate the address book model has changed */
    private void indicatePersonChanged() {
        raise(new PersonChangedEvent(person));
    }

    //@@author traceurgan
    /** Raises an event to indicate the journal model has changed */
    private void indicateJournalChanged() {
        raise(new JournalChangedEvent(journal));
    }

    //@@author
    @Override
    public synchronized void deletePerson() throws PersonNotFoundException {
        requireAllNonNull(this.person);
        updatePerson(null);
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson newPerson) throws DuplicatePersonException {
        requireAllNonNull(newPerson);

        updatePerson(newPerson);
    }

    @Override
    public void editPerson(ReadOnlyPerson editedPerson)
            throws NullPointerException {
        requireAllNonNull(this.person, editedPerson);
        updatePerson(editedPerson);
    }

    //@@author
    private void updatePerson(ReadOnlyPerson editedPerson) {
        person = (Person) editedPerson;
        indicatePersonChanged();
    }

    //@@author traceurgan
    @Override
    public ReadOnlyJournal getJournal() {
        return journal;
    }

    @Override
    public synchronized void addJournalEntry(JournalEntry journalEntry) throws Exception {
        if (journal.getLast().getDate().equals(journalEntry.getDate())) {
            journal.updateJournalEntry(journalEntry, journal.getLast());
        } else {
            journal.addJournalEntry(journalEntry);
            logger.info("journal entry added");
        }
        indicateJournalChanged();
    }

    @Override
    public void deleteTag(Tag tag) {
       // addressBook.removeTag(tag);
    }

    //=========== Filtered Journal List Accessors =============================================================


    //@@author traceurgan
    @Override
    public ObservableList<JournalEntry> getJournalEntryList() {
        return FXCollections.unmodifiableObservableList(journal.getJournalEntryList());
    }

    public JournalEntry getLast() {
        return journal.getLast();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return person.equals(other.person)
                && journal.equals(other.journal);
    }

}
