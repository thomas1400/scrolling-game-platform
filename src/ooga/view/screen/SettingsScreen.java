package ooga.view.screen;

import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import ooga.controller.ScreenController;

/*

  Class commented out because functionality could not be achieved in time for the project deadline,
  but the class still contains interesting and potentially useful code.

 */

//
///**
// * Settings screen for in-game settings changes.
// */
//public class SettingsScreen extends Screen {
//
//  private ComboBox<String> cameraManagerSelector;
//  private ComboBox<String> physicsSelector;
//  private EventHandler<? super MouseEvent> oldParentHandler;
//  private GameScreen parent;
//
//  /**
//   * Initializes a settings screen with a controller and GameScreen parent
//   * @param controller ScreenController
//   * @param parent GameScreen of game
//   */
//  SettingsScreen(ScreenController controller, GameScreen parent) {
//    super(controller);
//    this.parent = parent;
//
//    oldParentHandler = parent.getOnMouseClicked();
//    parent.setOnMouseClicked(e -> this.requestFocus());
//
//    cameraManagerSelector = new ComboBox<>();
//    cameraManagerSelector.getItems().addAll(controller.getCameraManagerOptions());
//
//    physicsSelector = new ComboBox<>();
//    physicsSelector.getItems().addAll(controller.getPhysicsOptions());
//
//    dynamicNodes.put("camera-manager-selector", cameraManagerSelector);
//    dynamicNodes.put("physics-selector", physicsSelector);
//
//    loadLayout();
//  }
//
//  /**
//   * Go back to the GameScreen. Public for REFLECTION.
//   */
//  protected void back() {
//    parent.getChildren().remove(this);
//    parent.setOnMouseClicked(oldParentHandler);
//    parent.resume();
//  }
//
//  /**
//   * Apply settings. Public for REFLECTION.
//   */
//  protected void apply() {
//    controller.setCameraManagerOption(cameraManagerSelector.getValue());
//    controller.setPhysicsOption(physicsSelector.getValue());
//    back();
//  }
//}