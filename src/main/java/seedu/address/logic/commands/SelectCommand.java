package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person shown in the main window\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.getPerson();
        } catch (NullPointerException npe) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand); // instanceof handles nulls
    }

}
