package seedu.address.logic.commands;

//@@author HEARTOFAL1ON
/**
 * Sends a motivational picture via the Browser Panel.
 */
public class MotivateCommand {

    public static final String COMMAND_WORD = "motivate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sends a motivational picture via Browser "
            + "Panel. ";
    public static final String MESSAGE_SUCCESS = "Picture sent!";

    /**
     * Creates an MotivateCommand to send to Browser Panel to motivate!
     */
    public MotivateCommand() { }

    private CommandResult getCommandResult() {
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
