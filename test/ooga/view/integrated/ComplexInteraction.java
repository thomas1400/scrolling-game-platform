package ooga.view.integrated;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.control.Labeled;
import javafx.stage.Stage;
import ooga.controller.OogaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class ComplexInteraction extends ApplicationTest{

  private Labeled subtitle;

  @Override
  public void start(Stage primaryStage) {
    new OogaController(primaryStage);
    primaryStage.setX(0);
    primaryStage.setY(0);
  }

  @BeforeEach
  void selectDefaultGame() {
    clickOn(100, 300);
    clickOn(450, 550);
  }

  @Test
  void defaultUser() {
    navigateToLevelSelect();
    Labeled userLabel = lookup("#usernameLabel").queryLabeled();
    assertEquals("User : Default User", userLabel.getText());
  }

  @Test
  void levelSelectToMain() {
    navigateToLevelSelect();
    Labeled subtitle = lookup(".subtitle").queryLabeled();
    assertEquals("User", subtitle.getText().split("\\s+")[0]);
    clickOn(110, 570);
    Labeled title = lookup(".title").queryLabeled();
    assertEquals("OOGA", title.getText());
  }

  @Test
  void multipleGameSelect() {
    Labeled title = lookup(".title").queryLabeled();
    assertEquals("OOGA", title.getText());
    navigateToGameSelect();
    clickOn(400, 300);
    clickOn(450, 550);
    title = lookup(".title").queryLabeled();
    assertEquals("Super Mario", title.getText());
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
