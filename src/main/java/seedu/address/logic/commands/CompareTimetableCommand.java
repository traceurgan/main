package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowTimetableRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.person.timetable.TimetableComparatorUtil;

/**
 * Compares the partner's timetable with a given timetable
 */
public class CompareTimetableCommand extends Command {

    public static final String COMMAND_WORD = "tcompare";
    public static final String COMMAND_ALIAS = "tc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Compares your partner's timetable with a given timetable.\n"
            + "Parameters: " + PREFIX_TIMETABLE + "TIMETABLE_URL\n"
            + "Example: " + COMMAND_WORD + " http://modsn.us/wNuIW";

    public static final String MESSAGE_TIMETABLE_COMPARE_SUCCESS = "Compared timetable";

    private Timetable otherTimetable;

    public CompareTimetableCommand(Timetable otherTimetable) {
        this.otherTimetable = otherTimetable;
    }

    @Override
    public CommandResult execute() {
        ReadOnlyPerson partner = model.getPartner();
        TimetableComparatorUtil.compareTimetable(partner.getTimetable(), otherTimetable);

        EventsCenter.getInstance().post(new ShowTimetableRequestEvent());
        EventsCenter.getInstance().post(new JumpToListRequestEvent());
        return new CommandResult(MESSAGE_TIMETABLE_COMPARE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompareTimetableCommand // instanceof handles nulls
                && this.otherTimetable.equals(((CompareTimetableCommand) other).otherTimetable)); // state check
    }
}
