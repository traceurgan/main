package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.journalentry.JournalEntry;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {

    /** Clears existing backing model for journal and replaces with the provided new data. */
    void resetJournalData(ReadOnlyJournal newData);

    /** Clears existing backing model for person and replaces with the provided new data. */
    void resetPersonData(ReadOnlyPerson newData);

    /** Returns the Person */
    ReadOnlyPerson getPerson();

    /** Returns the Person as Observable list */
    ObservableList<ReadOnlyPerson> getPersonAsList();

    //@@author traceurgan
    /** Returns the Journal */
    ReadOnlyJournal getJournal();

    /** Adds the given person */
    void addJournalEntry(JournalEntry journalEntry) throws Exception;

    /** Returns an unmodifiable view of the journal entry list */
    ObservableList<JournalEntry> getJournalEntryList();

    //@@author
    void deletePerson() throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Edits the given person
     *
     * @throws NullPointerException
     *
     * */
    void editPerson(ReadOnlyPerson editedPerson)
            throws NullPointerException;

    String checkDate(int last);

    /** Removes the given {@code tag} from all {@code Person}s. */
    void deleteTag(Tag tag);

    int getLast();
}
