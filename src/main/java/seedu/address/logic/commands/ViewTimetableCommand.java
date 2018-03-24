package seedu.address.logic.commands;

//@@author marlenekoh
/**
 * Displays Timetable of the specified person in browser panel
 */
public class ViewTimetableCommand extends Command {

    public static final String COMMAND_WORD = "tview";
    public static final String COMMAND_ALIAS = "tv";

    public static final String MESSAGE_SUCCESS = "Timetable displayed";

    @Override
    public CommandResult execute() {
        //TODO: implement display timetable
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
