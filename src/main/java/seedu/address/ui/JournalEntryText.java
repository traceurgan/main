package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving journal entry text.
 */
public class JournalEntryText extends UiPart<Region> {

    private static final String FXML = "JournalEntryText.fxml";

    @FXML
    private TextArea journalTextArea;

    public JournalEntryText() {
        super(FXML);
    }

    public String getText() {
        return journalTextArea.getText();
    }

    public void setText(String text) {
        journalTextArea.setText(
                "Saving failed. Please save your text using Ctr + C before attempting to save again.\n" + text);
    }
}
