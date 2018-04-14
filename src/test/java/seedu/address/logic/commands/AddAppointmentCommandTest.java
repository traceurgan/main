package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalJournalEntries.getTypicalJournal;
import static seedu.address.testutil.TypicalPersons.getTypicalPerson;

import java.text.ParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddAppointmentParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment.Appointment;
import seedu.address.model.person.ReadOnlyPerson;

//@@author chenxing1992
public class AddAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() throws ParseException,  seedu.address.logic.parser.exceptions.ParseException {
        String arg = "Lunch, tomorrow 5pm";

        AddAppointmentCommand command = new AddAppointmentCommand(setAppointment(arg));
        AddAppointmentCommand command2 = new AddAppointmentCommand(setAppointment(arg));
        assertEquals(command, command2);
        assertNotEquals(command, new AddAppointmentCommand(setAppointment(arg)));
    }

    @Test
    public void execute() throws ParseException, CommandException {


        try {
            //Invalid date
            String arg = "lunch, yesterday 5pm";
            Command command = setCommand(setAppointment(arg));
            CommandResult result = command.execute();
            assertEquals(result.feedbackToUser, AddAppointmentCommand.INVALID_DATE);

            //Set to valid date
            arg = "lunch, tomorrow 5pm";
            command = setCommand(setAppointment(arg));
            result = command.execute();
            assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS);

            //Set to valid date with end time
            arg = "lunch, tomorrow 5pm to 7pm";
            command = setCommand(setAppointment(arg));
            result = command.execute();
            assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS);
        } catch (seedu.address.logic.parser.exceptions.ParseException ive) {
            fail();
        }

    }

    @Test
    public void outOfBoundsIndex() throws CommandException, seedu.address.logic.parser.exceptions.ParseException {
        thrown.expect(CommandException.class);
        setCommand(AddAppointmentParser.getAppointmentFromString("lunch,tomorrow 5pm")).execute();
    }

    /**
     * Util methods to set appointment command
     */
    private Command setCommand(Appointment appointment) {
        AddAppointmentCommand command = new AddAppointmentCommand(appointment);

        Model model = new ModelManager(getTypicalPerson(),getTypicalJournal(), new UserPrefs());
        command.setData(model);
        return command;
    }

    private Appointment setAppointment(String str) throws seedu.address.logic.parser.exceptions.ParseException {

        return AddAppointmentParser.getAppointmentFromString(str);
    }

}
