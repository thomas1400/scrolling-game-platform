
package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.control.RadioButton;
import ooga.Main;
import ooga.controller.levels.BasicLevel;
import ooga.view.dynamicUI.LevelSelectorTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class UITest extends ApplicationTest {

  private LevelSelectorTool myLevelSelectorTool;
  private RadioButton myRadioButton;

  @BeforeEach
  public void setUp () throws Exception {
    // start GUI new for each test
    launch(Main.class);
    myLevelSelectorTool = lookup("#tested").queryAs(LevelSelectorTool.class);
    myRadioButton = lookup("#button1").queryAs(RadioButton.class);
  }

  @Test
  public void testLevelSelectorUnlockingAction() {
    boolean expectedChange = true;
    BasicLevel initial = myLevelSelectorTool.getSelected();

    clickOn(myRadioButton);

    BasicLevel finall = myLevelSelectorTool.getSelected();
    assertEquals(expectedChange, initial == finall);
  }


}

