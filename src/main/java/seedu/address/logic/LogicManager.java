package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.JournalChangedEvent;
import seedu.address.commons.events.model.SaveEntryEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SaveEntryCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.journalEntry.JournalEntry;
import seedu.address.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    public CommandResult execute(JournalEntry journalEntry) throws CommandException {
        try {
            SaveEntryCommand command = new SaveEntryCommand(journalEntry);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            return result;
        } finally {
            history.add("Entry saved.");
        }
    }

    @Subscribe
    public void handleSaveEntryEvent(SaveEntryEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Attempt to save"));
        try {
            model.addJournalEntry(event.journalEntry);
        } catch (Exception e) {
            logger.info("Save failed");
        }
    }



    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
