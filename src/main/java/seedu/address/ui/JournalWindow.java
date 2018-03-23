package seedu.address.ui;

import java.time.LocalDate;

import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for a journal page
 */
public class JournalWindow extends UiPart<Stage> {

    private static final String FXML = "JournalWindow.fxml";

    private JournalWindow (Stage root) {
        super (FXML, root);

        String date = concatenateDate();

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

    public void show() {
        getRoot().show();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }
}
