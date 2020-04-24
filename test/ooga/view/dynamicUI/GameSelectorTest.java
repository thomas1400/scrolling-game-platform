package ooga.view.dynamicUI;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.controller.users.UserList;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class GameSelectorTest extends ApplicationTest {

  private ScreenController sc;

  @Override
  public void start(Stage primaryStage) {
    sc = new ScreenController(primaryStage, new UserList());
    primaryStage.setX(0);
    primaryStage.setY(0);
  }

  @Test
  void gameSelectionNullPreLaunch() {
    assertNull(sc.getGameType());
    clickOn(450, 550);
    assertNotNull(sc.getGameType());
  }

  @Test
  void gameSelection1() {
    clickOn(100, 300);
    clickOn(450, 550);
    assertEquals("flappy", sc.getGameType());
  }

  @Test
  void gameSelection2() {
    clickOn(400, 300);
    clickOn(450, 550);
    assertEquals("mario", sc.getGameType());
  }

  @Test
  void gameSelection3() {
    clickOn(600, 300);
    clickOn(450, 550);
    assertEquals("doodlejump", sc.getGameType());
  }


}
