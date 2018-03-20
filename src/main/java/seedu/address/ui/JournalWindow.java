package seedu.address.ui;


import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller for a journal page
 */
//public class JournalWindow extends UiPart<Stage> {
public class JournalWindow {

    public void makeBox (Stage stage) {
        AnchorPane ap = new AnchorPane();

        TextArea journalTextArea = new TextArea();
        AnchorPane.setLeftAnchor(journalTextArea, 0.0);
        AnchorPane.setTopAnchor(journalTextArea, 0.0);
        AnchorPane.setRightAnchor(journalTextArea, 0.0);
        AnchorPane.setBottomAnchor(journalTextArea, 0.0);

        ap.getChildren().add(journalTextArea);
        Scene scene = new Scene(ap);

        stage.setScene(scene);
        stage.setTitle("Journal");
        stage.show();
    }

//    public void show() {
//        getRoot().show();
//    }
}
