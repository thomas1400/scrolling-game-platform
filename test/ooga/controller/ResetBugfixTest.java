package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.levels.BasicLevel;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.users.UserList;
import ooga.view.dynamicUI.LevelSelectorTool;
import ooga.view.screen.LevelSelectorScreen;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class ResetBugfixTest extends ApplicationTest {

  private ScreenController sc;

  @Override
  public void start(Stage primaryStage) throws Exception {
    sc = new ScreenController(primaryStage, new UserList());
    primaryStage.setX(0);
    primaryStage.setY(0);
  }

  @Test
  void testReset() throws InterruptedException {
    clickOn(400, 300);
    clickOn(450, 550);

    navigateToLevelSelect();

    clickOn(700, 550);

    sleep(1000);

    clickOn(400, 570);

    sleep(4000);

    assertEquals("GameScreen", sc.getCurrentScreen());
  }

  private void navigateToUserSelect() {
    clickOn(370, 570);
  }

  private void navigateToLevelSelect() {
    clickOn(110, 570);
  }

  private void navigateToGameSelect() {
    clickOn(600, 570);
  }

}
