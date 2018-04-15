package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalJournalEntries.getTypicalJournal;
import static seedu.address.testutil.TypicalPersons.getTypicalPerson;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowTimetableRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ShowTimetableCommand}.
 */
@Ignore
public class ShowTimetableCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPerson(), getTypicalJournal(), new UserPrefs());
    }

    //TODO: ShowTimetableCommandTest
    @Test
    public void equals() {
        ShowTimetableCommand showTimetableCommand = new ShowTimetableCommand();

        // different types -> returns false
        assertFalse(showTimetableCommand.equals(1));

        // null -> returns false
        assertFalse(showTimetableCommand.equals(null));
    }

    @Test
    public void assertExecutionSuccess() {
        ShowTimetableCommand showTimetableCommand = prepareCommand();

        try {
            CommandResult commandResult = showTimetableCommand.execute();
            assertEquals(ShowTimetableCommand.MESSAGE_SELECT_PERSON_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowTimetableRequestEvent);
    }

    /**
     * Returns a {@code ShowTimetableCommand} with new CommandHistory and new UndoRedoStack.
     */
    private ShowTimetableCommand prepareCommand() {
        ShowTimetableCommand showTimetableCommand = new ShowTimetableCommand();
        showTimetableCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return showTimetableCommand;
    }
}
