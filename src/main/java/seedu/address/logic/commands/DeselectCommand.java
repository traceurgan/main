package seedu.address.logic.commands;

import static seedu.address.model.person.Person.PARTNER_INDEX;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.TimetableChangedEvent;
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

    private final Index targetIndex;

    public DeselectCommand() {
        this.targetIndex = Index.fromZeroBased(PARTNER_INDEX);
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson partner = lastShownList.get(targetIndex.getZeroBased());
        EventsCenter.getInstance().post(new HideTimetableRequestEvent());

        return new CommandResult(String.format(MESSAGE_DESELECT_PERSON_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeselectCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeselectCommand) other).targetIndex)); // state check
    }

}
