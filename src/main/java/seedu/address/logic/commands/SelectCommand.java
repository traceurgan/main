package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowTimetableRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects your partner from NUSCouples.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects your partner and shows his/her timetable.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Showing timetable of: ";

    private ReadOnlyPerson partner;

    @Override
    public CommandResult execute() throws CommandException {

        try {
            partner = model.getPerson();
            EventsCenter.getInstance().post(new ShowTimetableRequestEvent(partner.getTimetable()));
            EventsCenter.getInstance().post(new JumpToListRequestEvent());
        } catch (NullPointerException npe) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }

        return new CommandResult(MESSAGE_SELECT_PERSON_SUCCESS + partner);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand); // instanceof handles nulls
    }

}
