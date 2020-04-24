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

  private static final String DATA_PATH_PREFIX = "data/";
  private static final String GAME_DATA_PATH = "gamedata/";
  public static final String VIEW_PATH = "view/";
  private static final String RESOURCES_PATH = "resources/";
  private static final String RESOURCES_SUFFIX = "Text";
  private static final String BUTTON_ACTIONS_SUFFIX = "Buttons";
  public static final String FXLR_SUFFIX = ".fxlr";

  protected ScreenController controller;
  private ResourceBundle buttonActions;
  protected ResourceBundle resources;
  Map<String, Node> dynamicNodes;

  public Screen(ScreenController controller) {
    this.controller = controller;
    this.dynamicNodes = new HashMap<>();
    initializeResources();
    initializeButtonActions();
    initializeScreen();
  }

  private void initializeResources() {
    try {
      File file = new File(DATA_PATH_PREFIX + GAME_DATA_PATH + controller.getGameType() + "/" + VIEW_PATH
          + RESOURCES_PATH);
      URL[] urls = {file.toURI().toURL()};
      ClassLoader loader = new URLClassLoader(urls);

      resources = ResourceBundle.getBundle(this.getClass().getSimpleName() + RESOURCES_SUFFIX,
          Locale.getDefault(), loader);

    } catch (MalformedURLException | MissingResourceException e) {
      try {
        File file = new File(DATA_PATH_PREFIX + VIEW_PATH + RESOURCES_PATH);
        URL[] urls = {file.toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);

        resources = ResourceBundle.getBundle(this.getClass().getSimpleName() + RESOURCES_SUFFIX,
            Locale.getDefault(), loader);
      }
      catch (MalformedURLException | MissingResourceException f) {
        ExceptionFeedback.throwBreakingException(f, "Unable to load resources at path: " +
            DATA_PATH_PREFIX + VIEW_PATH + RESOURCES_PATH);
      }
    }
  }

  private void initializeButtonActions() {
    try {
      File file = new File(DATA_PATH_PREFIX + GAME_DATA_PATH + controller.getGameType() + "/" + VIEW_PATH
          + RESOURCES_PATH);
      URL[] urls = {file.toURI().toURL()};
      ClassLoader loader = new URLClassLoader(urls);

      buttonActions = ResourceBundle.getBundle(this.getClass().getSimpleName() + BUTTON_ACTIONS_SUFFIX,
          Locale.getDefault(), loader);
    } catch (MalformedURLException | MissingResourceException e) {
      try {
        File file = new File(DATA_PATH_PREFIX + VIEW_PATH + RESOURCES_PATH);
        URL[] urls = {file.toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);

        buttonActions = ResourceBundle.getBundle(this.getClass().getSimpleName() + BUTTON_ACTIONS_SUFFIX,
            Locale.getDefault(), loader);
      }
      catch (MalformedURLException | MissingResourceException f) {
        ExceptionFeedback.throwBreakingException(f, "Unable to load button actions at path: " +
            DATA_PATH_PREFIX + VIEW_PATH + RESOURCES_PATH);
      }
    }
  }

  protected void initializeScreen() {
    this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
  }

  protected void loadLayout() {
    try {
      new FXLRParser().loadFXLRLayout(this,
          new File(DATA_PATH_PREFIX + GAME_DATA_PATH + controller.getGameType() + "/" + VIEW_PATH + this.getClass().getSimpleName() + FXLR_SUFFIX));
    } catch (FileNotFoundException e) {
      try {
        new FXLRParser().loadFXLRLayout(this,
            new File(DATA_PATH_PREFIX + VIEW_PATH + this.getClass().getSimpleName() + FXLR_SUFFIX));
      } catch (FileNotFoundException f) {
        ExceptionFeedback.throwBreakingException(e, "Could not load layout for path: \n + "
            + DATA_PATH_PREFIX + VIEW_PATH + this.getClass().getSimpleName() + FXLR_SUFFIX
        );
      }
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
