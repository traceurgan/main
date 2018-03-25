package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.journalEntry.JournalEntry;

public class SaveEntryCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_SUCCESS = "New entry added: %1$s";

    private final JournalEntry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SaveEntryCommand(JournalEntry journalEntry) {
        toAdd = journalEntry;

    }

    @Override
    public CommandResult execute() throws CommandException{
        requireNonNull(model);

        return getCommandResult();
    }

    private CommandResult getCommandResult() throws CommandException {
        try {
            model.addJournalEntry(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (Exception e) {
            throw new CommandException("Error");
        }
    }
}
