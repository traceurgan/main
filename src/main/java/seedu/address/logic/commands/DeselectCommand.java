package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HideTimetableRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Deselects your partner from NUSCouples.
 */
public class DeselectCommand extends Command {
    public static final String COMMAND_WORD = "deselect";
    public static final String COMMAND_ALIAS = "des";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deselects your partner and hides his/her timetable.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DESELECT_PERSON_SUCCESS = "Deselected Person: %1$s";

    private ReadOnlyPerson partner;

    @Override
    public CommandResult execute() throws CommandException {

        try {
            partner = model.getPerson();
            partner.getTimetable();
            EventsCenter.getInstance().post(new HideTimetableRequestEvent());
        } catch (NullPointerException npe) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }
        return new CommandResult(String.format(MESSAGE_DESELECT_PERSON_SUCCESS, partner));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeselectCommand // instanceof handles nulls
                && this.partner.equals(((DeselectCommand) other).partner)); // state check
    }

}
