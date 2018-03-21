package seedu.address.logic.commands;

import javafx.stage.Stage;
import seedu.address.ui.JournalWindow;

/**
 * Creates a new window for typing journal entries
 */
public class NewJournal extends Command {

    public static final String COMMAND_WORD = "new";
    public static final String COMMAND_ALIAS = "n";

    public static final String NEW_JOURNAL_ENTRY_CREATED = "New Journal Entry Created.";

    @Override
    public CommandResult execute() {
        Stage secondaryStage = new Stage();
        JournalWindow journalWindow = new JournalWindow(secondaryStage);
        journalWindow.show();

        return new CommandResult(NEW_JOURNAL_ENTRY_CREATED);
    }
}
