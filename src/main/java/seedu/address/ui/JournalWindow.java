package seedu.address.ui;


import javax.swing.JTextArea;
import javax.swing.JFrame;

public class JournalWindow extends JFrame {
    public JournalWindow() {
        final JTextArea textArea = new JTextArea();
        getContentPane().add(textArea);
    }
    //do something

}
