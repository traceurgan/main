package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.SaveEntryEvent;
import seedu.address.model.journalentry.JournalEntry;

/**
 * Controller for a journal page
 */
public class JournalWindow extends UiPart<Stage> {

    private static final String FXML = "JournalWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private JournalEntryText journalEntryText;
    private String date;

    @FXML
    private StackPane journalTextPlaceholder;

    private JournalWindow (Stage root) {
        super (FXML, root);

        this.date = concatenateDate();
        fillInnerParts();

        root.setTitle(date + " - Journal");
        root.initModality(Modality.APPLICATION_MODAL);
    }

    private JournalWindow (Stage root, String date, String text) {
        super (FXML, root);

        fillInnerParts(text);
        this.date = date;

        root.setTitle(date + " - Journal");
        root.initModality(Modality.APPLICATION_MODAL);

    }

    public JournalWindow(String date, String text) {
        this(new Stage(), date, text);
    }

    public JournalWindow () {
        this(new Stage());
    }

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

    /**
     * Fills placeholder with a editable TextArea
     */
    private void fillInnerParts() {
        journalEntryText = new JournalEntryText();
        journalTextPlaceholder.getChildren().add(journalEntryText.getRoot());
    }

    /**
     * Fills placeholder with a editable TextArea
     */
    private void fillInnerParts(String text) {
        journalEntryText = new JournalEntryText();
        journalEntryText.setText(text);
        journalTextPlaceholder.getChildren().add(journalEntryText.getRoot());
    }

    /**
     * Raise JournalEntrySaveEvent on journal window close if text area is not empty
     */
    @FXML
    private void handleJournalClose() {
        if (!journalEntryText.getText().isEmpty()) {
            JournalEntry journalEntry = new JournalEntry(this.date, journalEntryText.getText());
            raise(new SaveEntryEvent(journalEntry));
        }
        return;
    }

    public void show() {
        getRoot().show();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

}
