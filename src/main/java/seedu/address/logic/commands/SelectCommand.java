package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.person.timetable.TimetableModuleSlot;
import seedu.address.model.person.timetable.TimetableUtil;

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

    public SelectCommand() { }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            partner = model.getPartner();
            Timetable timetable = partner.getTimetable();

            ArrayList<TimetableModuleSlot> unsortedModuleSlots =
                    TimetableUtil.setUpUnsortedModuleSlotsForViewing(partner.getTimetable());
            timetable.setAllModulesSlots(unsortedModuleSlots);

            HashMap<String, ArrayList<TimetableModuleSlot>> sortedModuleSlots =
                    TimetableUtil.sortModuleSlotsByDay(unsortedModuleSlots);
            timetable.setListOfDays(sortedModuleSlots);

            TimetableUtil.setTimetableDisplayInfo(timetable);
            TimetableUtil.setUpTimetableInfo(timetable);

            model.indicateTimetableChanged(timetable);
            model.requestShowTimetable();
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, partner));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand); // instanceof handles nulls
    }

}
