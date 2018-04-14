package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalJournalEntries.getTypicalJournal;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
@Ignore
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalJournal(), new UserPrefs());
    }

    //TODO: SelectCommandTest
    @Test
    public void equals() {
        SelectCommand selectCommand = new SelectCommand();

        // different types -> returns false
        assertFalse(selectCommand.equals(1));

        // null -> returns false
        assertFalse(selectCommand.equals(null));
    }

    /**
     * Returns a {@code SelectCommand} with new CommandHistory and new UndoRedoStack.
     */
    private SelectCommand prepareCommand() {
        SelectCommand selectCommand = new SelectCommand();
        selectCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectCommand;
    }
}
