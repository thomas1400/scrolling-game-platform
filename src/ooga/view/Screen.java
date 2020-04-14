package ooga.view;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import ooga.controller.ScreenController;

public class Screen extends Pane implements Displayable {

  private static final int PREF_WIDTH = 800, PREF_HEIGHT = 600;
  static final String FONT_FAMILY = "Cambria";
  static final int TITLE_FONT_SIZE = 40;
  static final int BUTTON_FONT_SIZE = 20;
  static final int DETAIL_FONT_SIZE = 12;
  static final double PADDING = 10.0;

  private static final String RESOURCES_PATH = "ooga.view.resources.";
  private static final String RESOURCES_SUFFIX = "Text";
  private static final String BUTTON_ACTIONS_SUFFIX = "Buttons";

  protected double workingHeight, workingWidth;
  protected ScreenController controller;
  protected ResourceBundle buttonActions;
  protected ResourceBundle resources;

  public Screen(ScreenController controller) {
    this.controller = controller;
    initializeResources();
    initializeScreen();
  }

  protected void initializeResources() {
    resources = ResourceBundle.getBundle(
        RESOURCES_PATH + this.getClass().getSimpleName() + RESOURCES_SUFFIX
    );
    buttonActions = ResourceBundle.getBundle(
        RESOURCES_PATH + this.getClass().getSimpleName() + BUTTON_ACTIONS_SUFFIX
    );
  }

  protected void initializeScreen() {
    this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
  }

  protected void setWorkingDimensions(int vPanels, int hPanels) {
    workingHeight = this.getPrefHeight() - (vPanels+1) * PADDING;
    workingWidth = this.getPrefWidth() - (hPanels+1) * PADDING;
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

  @Override
  public Node getDisplay() {
    return this;
  }

}
