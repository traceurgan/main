package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalJournalEntries.getTypicalJournal;
import static seedu.address.testutil.TypicalPersons.getTypicalPerson;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewTimetableCommand}.
 */
@Ignore
public class ViewTimetableCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPerson(), getTypicalJournal(), new UserPrefs());
    }

    //TODO: ViewTimetableCommandTest
    @Test
    public void equals() {
        ViewTimetableCommand viewTimetableCommand = new ViewTimetableCommand();

        // different types -> returns false
        assertFalse(viewTimetableCommand.equals(1));

        // null -> returns false
        assertFalse(viewTimetableCommand.equals(null));
    }

    /**
     * Returns a {@code ViewTimetableCommand} with new CommandHistory and new UndoRedoStack.
     */
    private ViewTimetableCommand prepareCommand() {
        ViewTimetableCommand viewTimetableCommand = new ViewTimetableCommand();
        viewTimetableCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewTimetableCommand;
    }
}
