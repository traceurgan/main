package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.SaveEntryEvent;
import seedu.address.model.journalEntry.JournalEntry;

/**
 * Controller for a journal page
 */
public class JournalWindow extends UiPart<Stage> {

    static String testDate = "1";

    private static final String FXML = "JournalWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private JournalEntryText journalEntryText;
    private String date;

    @FXML
    private AnchorPane journalTextPlaceholder;

    private JournalWindow (Stage root) {
        super (FXML, root);

        date = concatenateDate();
        fillInnerParts();

        root.setTitle(date + " - Journal");
        root.initModality(Modality.APPLICATION_MODAL);
    }

    public JournalWindow () {
        this(new Stage());
    }

    private String concatenateDate() {
        LocalDate currentDate = LocalDate.now();
        int dd = currentDate.getDayOfMonth();
        int mm = currentDate.getMonth().getValue();
        int yyyy = currentDate.getYear();
        return String.format("%04d", yyyy) + String.format("%02d", mm) + String.format("%02d", dd);
    }

    void fillInnerParts() {
        journalEntryText = new JournalEntryText();
        journalTextPlaceholder.getChildren().add(journalEntryText.getRoot());
    }

    @FXML
    private void handleJournalClose() throws Exception {
        testDate = String.valueOf(Integer.valueOf(testDate) + 1);
        logger.info(String.format(this.date + " " + journalEntryText.getText()));
        JournalEntry journalEntry = new JournalEntry(testDate, journalEntryText.getText());
        raise(new SaveEntryEvent(journalEntry));
    }

    public void show() {
        getRoot().show();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

}
