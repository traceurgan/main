package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

public class JournalWindowTest extends GuiUnitTest {

    private JournalWindow journalWindow;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> journalWindow = new JournalWindow());
        FxToolkit.showStage();

    }

    @Test
    public void isShowing_journalWindowIsShowing_true() {
        guiRobot.interact(() -> journalWindow.show());
        assertTrue(journalWindow.isShowing());
    }

    @Test
    public void focus_journalWindowStaysFocused_focused() {
        guiRobot.interact(() -> journalWindow.show());
        assertTrue(journalWindow.getRoot().isFocused());

        guiRobot.interact(() -> journalWindow.getRoot().hide());
        assertFalse(journalWindow.getRoot().isFocused());

        guiRobot.interact(() -> journalWindow.show());
        guiRobot.removeFocus();
        assertTrue(journalWindow.getRoot().isFocused());
    }

    @Test
    public void isShowing_journalWindowIsHiding_false() {
        guiRobot.interact(() -> journalWindow.getRoot().hide());
        assertFalse(journalWindow.isShowing());
    }
}
