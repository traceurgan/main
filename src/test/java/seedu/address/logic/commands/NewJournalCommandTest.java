package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.NewJournalCommand.NEW_JOURNAL_ENTRY_CREATED;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.ui.testutil.EventsCollectorRule;

//@@author traceurgan
public class NewJournalCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_newjournal_success() {
        CommandResult result = new NewJournalCommand().execute();
        assertEquals(NEW_JOURNAL_ENTRY_CREATED, result.feedbackToUser);
    }
}
