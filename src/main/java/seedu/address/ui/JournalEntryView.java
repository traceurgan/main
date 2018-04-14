package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.model.journalentry.Date;
import seedu.address.model.journalentry.JournalEntry;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class JournalEntryView extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "JournalEntryView.fxml";

    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty text = new SimpleStringProperty("");

    @FXML
    private Text journalDate;

    @FXML
    private Text journalText;

    public JournalEntryView(JournalEntry journalEntry) {
        super(FXML);
        date.setValue(journalEntry.getDate().value);
        text.setValue(journalEntry.getText());
        journalDate.textProperty().bind(date);
        journalText.textProperty().bind(text);
    }
}
