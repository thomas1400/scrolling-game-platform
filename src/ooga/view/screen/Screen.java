package ooga.view.screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import ooga.controller.ScreenController;
import ooga.exceptions.ExceptionFeedback;
import ooga.view.fxlr.FXLRParser;

public abstract class Screen extends Pane {

  private static final int PREF_WIDTH = 800, PREF_HEIGHT = 600;

  private static final String RESOURCES_PATH = "ooga.view.resources.";
  private static final String RESOURCES_SUFFIX = "Text";
  private static final String BUTTON_ACTIONS_SUFFIX = "Buttons";

  protected ScreenController controller;
  private ResourceBundle buttonActions;
  protected ResourceBundle resources;
  Map<String, Node> dynamicNodes;

  public Screen(ScreenController controller) {
    this.controller = controller;
    this.dynamicNodes = new HashMap<>();
    initializeResources();
    initializeScreen();
  }

  private void initializeResources() {
    resources = ResourceBundle.getBundle(
        RESOURCES_PATH + this.getClass().getSimpleName() + RESOURCES_SUFFIX
    );
    buttonActions = ResourceBundle.getBundle(
        RESOURCES_PATH + this.getClass().getSimpleName() + BUTTON_ACTIONS_SUFFIX
    );
  }

  private void initializeScreen() {
    this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
  }

  protected void loadLayout() {
    try {
      new FXLRParser().loadFXLRLayout(this,
          new File("resources/view/" + this.getClass().getSimpleName() + ".fxlr"));
    } catch (FileNotFoundException e) {
      ExceptionFeedback.throwBreakingException(e,
          "Resources file not found for screen " + this.getClass().getSimpleName() + ". " +
              "Unable to display application."
      );
    }
  }

  public void handleButtonPress(String tag) {
    try {
      String action = buttonActions.getString(tag);
      if (action.length() > 1) {
        if (action.charAt(0) == '>') {                      // > encoding to switch screens
          controller.switchToScreen(action.substring(1));
        } else if (action.charAt(0) == '~') {       // ~ encoding to do action on this screen
          String method = action.substring(1);
          try {
            // invoke named method with null parameters for button action
            this.getClass().getDeclaredMethod(method).invoke(this);
          } catch (Exception e) {
            ExceptionFeedback.throwBreakingException(e,
                "Unable to perform button action for button tag " + tag + ".");
          }
        }
      }
    } catch (MissingResourceException ignored) { }
  }

  public String getResource(String tag) {
    return resources.getString(tag);
  }

  public Node getDynamicUIElement(String tag) {
    return dynamicNodes.get(tag);
  }

}
