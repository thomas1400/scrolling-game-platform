package ooga.view.fxlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ooga.controller.OogaController;
import ooga.controller.ScreenController;
import ooga.controller.users.UserList;
import ooga.exceptions.ExceptionFeedback;
import ooga.view.screen.HomeScreen;
import ooga.view.screen.Screen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class FXLRTest extends ApplicationTest {

  private ScreenController sc;
  private FXLRParser fxlrParser;
  private Screen testingScreen;
  private Stage primaryStage;
  private Labeled testLabel;
  private HBox hbox;

  @Override
  public void start(Stage primaryStage) {
    sc = new ScreenController(new Stage(), new UserList());

    fxlrParser = new FXLRParser();
    testingScreen = new HomeScreen(sc);
    testingScreen.getChildren().clear();
    primaryStage.setScene(new Scene(testingScreen));
    try {
      fxlrParser.loadFXLRLayout(testingScreen, new File("test/ooga/view/fxlr/FXLRTest.fxlr"));
    } catch (FileNotFoundException e) {
      ExceptionFeedback.throwHandledException(e, "File not found for test.");
    }
    testLabel = lookup(".label").queryLabeled();
  }

  @BeforeEach
  private void setup() {

  }

  @Test
  void testParsingStyleControl() {
    System.out.println(testLabel.getStyleClass());
    assertTrue(testLabel.getStyleClass().contains("label"));
  }

  @Test
  void testParsingTextControl() {
    assertEquals("OOGA", testLabel.getText());
  }

}
