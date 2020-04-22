package ooga.view.screen;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ooga.controller.ScreenController;

public class SettingsScreen extends Screen {

  private ComboBox<String> cameraManagerSelector;
  private ComboBox<String> physicsSelector;
  private EventHandler<? super MouseEvent> oldParentHandler;
  private GameScreen parent;

  public SettingsScreen(ScreenController controller, GameScreen parent) {
    super(controller);
    this.parent = parent;

    oldParentHandler = parent.getOnMouseClicked();
    parent.setOnMouseClicked(e -> this.requestFocus());

    cameraManagerSelector = new ComboBox<>();
    cameraManagerSelector.getItems().addAll(controller.getCameraManagerOptions());

    physicsSelector = new ComboBox<>();
    physicsSelector.getItems().addAll(controller.getPhysicsOptions());

    dynamicNodes.put("camera-manager-selector", cameraManagerSelector);
    dynamicNodes.put("physics-selector", physicsSelector);

    loadLayout();
  }

  public void back() {
    parent.getChildren().remove(this);
    parent.setOnMouseClicked(oldParentHandler);
    parent.resume();
  }

  public void apply() {
    controller.setCameraManagerOption(cameraManagerSelector.getValue());
    controller.setPhysicsOption(physicsSelector.getValue());
    back();
  }
}
