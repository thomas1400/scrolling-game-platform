package ooga.view.integrated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import ooga.controller.OogaController;
import ooga.controller.ScreenController;
import ooga.controller.users.UserList;
import ooga.view.dynamicUI.UserSelector;
import ooga.view.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class UserSelectorTest extends ApplicationTest {

  private Labeled userLabel;

  @Override
  public void start(Stage primaryStage) {
    new OogaController(primaryStage);
    primaryStage.setX(0);
    primaryStage.setY(0);
    //System.out.println(lookup(".user-selector"));
  }

  @BeforeEach
  void setup() {
    clickOn(100, 300);
    clickOn(450, 550);
  }

  @Test
  void defaultUser() {
    navigateToLevelSelect();
    userLabel = lookup("#usernameLabel").queryLabeled();
    assertEquals("User : Default User", userLabel.getText());
  }

  @Test
  void gameSelection1() {
    navigateToUserSelect();
    clickOn(100, 300);
    clickOn(450, 500);
    navigateToLevelSelect();
    userLabel = lookup("#usernameLabel").queryLabeled();
    assertEquals("User : ALL UNLOCKED", userLabel.getText());
  }

  private void navigateToUserSelect() {
    clickOn(400, 570);
  }

  private void navigateToLevelSelect() {
    clickOn(110, 570);
  }


}
