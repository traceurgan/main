package seedu.address.logic.commands;

import java.time.LocalDate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowJournalWindowRequestEvent;

/**
 * Creates a new window for typing journal entries
 */
public class NewJournalCommand extends Command {

    public static final String COMMAND_WORD = "jnew";
    public static final String COMMAND_ALIAS = "jn";

    public static final String NEW_JOURNAL_ENTRY_CREATED = "Journal Opened.";

    /**
     * Gets current local date and concatenates into a String in the form "yyyymmdd"
     */
    private String concatenateDate() {
        LocalDate currentDate = LocalDate.now();
        int dd = currentDate.getDayOfMonth();
        int mm = currentDate.getMonth().getValue();
        int yyyy = currentDate.getYear();
        return String.format("%04d", yyyy) + String.format("%02d", mm) + String.format("%02d", dd);
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowJournalWindowRequestEvent(concatenateDate()));
        return new CommandResult(NEW_JOURNAL_ENTRY_CREATED);
    }
}
