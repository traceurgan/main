package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author marlenekoh
/**
 * Deselects your partner from NUSCouples.
 */
public class ViewCalendarCommand extends Command {
    public static final String COMMAND_WORD = "cview";
    public static final String COMMAND_ALIAS = "cv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deselects your partner and shows your calendar view.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DESELECT_PERSON_SUCCESS = "Deselected Person: %1$s";

    private ReadOnlyPerson partner;

    public ViewCalendarCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            partner = model.getPartner();
            partner.getTimetable();
            model.requestHideTimetable();
        } catch (NullPointerException npe) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }
        return new CommandResult(String.format(MESSAGE_DESELECT_PERSON_SUCCESS, partner));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCalendarCommand // instanceof handles nulls
                && this.partner.equals(((ViewCalendarCommand) other).partner)); // state check
    }

}
