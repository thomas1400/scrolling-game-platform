package ooga.view.screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ooga.controller.ScreenController;
import ooga.exceptions.ExceptionFeedback;
import ooga.view.fxlr.FXLRParser;

public abstract class Screen extends Pane {

  private static final int PREF_WIDTH = 800, PREF_HEIGHT = 600;

  private static final String RESOURCES_PATH_PREFIX = "data/gamedata/";
  private static final String RESOURCES_RELATIVE_PATH = "resources/";
  private static final String RESOURCES_SUFFIX = "Text";
  private static final String BUTTON_ACTIONS_SUFFIX = "Buttons";
  public static final String FXLR_SUFFIX = ".fxlr";
  public static final String VIEW_PACKAGE = "/view/";

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
    try {
      File file = new File(RESOURCES_PATH_PREFIX + controller.getGameType() + VIEW_PACKAGE
          + RESOURCES_RELATIVE_PATH);
      URL[] urls = {file.toURI().toURL()};
      ClassLoader loader = new URLClassLoader(urls);

      resources = ResourceBundle.getBundle(this.getClass().getSimpleName() + RESOURCES_SUFFIX,
          Locale.getDefault(), loader);
      buttonActions = ResourceBundle.getBundle(this.getClass().getSimpleName() + BUTTON_ACTIONS_SUFFIX,
          Locale.getDefault(), loader);
    } catch (MalformedURLException e) {
      ExceptionFeedback.throwBreakingException(e, "Unable to load resources at path: " +
          RESOURCES_PATH_PREFIX + controller.getGameType() + VIEW_PACKAGE + RESOURCES_RELATIVE_PATH);
    }
  }

  protected void initializeScreen() {
    this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
  }

  protected void loadLayout() {
    try {
      new FXLRParser().loadFXLRLayout(this,
          new File(RESOURCES_PATH_PREFIX + controller.getGameType() + VIEW_PACKAGE + this.getClass().getSimpleName() + FXLR_SUFFIX));
    } catch (FileNotFoundException e) {
      ExceptionFeedback.throwBreakingException(e, "Could not load layout for path: \n + "
          + RESOURCES_PATH_PREFIX + controller.getGameType() + VIEW_PACKAGE +
          this.getClass().getSimpleName() + FXLR_SUFFIX
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
            // FIXME : remove printStackTrace()
            e.printStackTrace();
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
