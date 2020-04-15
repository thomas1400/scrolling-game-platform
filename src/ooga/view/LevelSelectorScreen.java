package ooga.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ooga.controller.ScreenController;
import ooga.exceptions.ExceptionFeedback;
import ooga.controller.data.BasicLevel;
import ooga.controller.data.BasicLevelList;
import ooga.controller.data.User;
import ooga.view.dynamicUI.DynamicUserLabel;
import ooga.view.dynamicUI.LevelProgressBar;
import ooga.view.dynamicUI.LevelSelectorTool;
import ooga.view.factory.ControlFactory;

public class LevelSelectorScreen extends Screen {

  private static final String LEVEL_GRAPH_FILE = "resources/levels/LevelGraph.txt";
  private static final String LEVEL_MAP_FILE = "resources/levels/LevelMap.txt";

  private BasicLevelList myLevels;
  private LevelSelectorTool lst;

  public LevelSelectorScreen(ScreenController controller, BasicLevelList levels) {
    super(controller);
    myLevels = levels;

    if (controller.getUsers() != null) {
      User user = controller.getUsers().getSelectedUser();

      DynamicUserLabel username = new DynamicUserLabel();
      username.setText(resources.getString("user") + " : " + user.getName());
      LevelProgressBar lpb = new LevelProgressBar(1, 3);
      lst = new LevelSelectorTool(levels, LEVEL_GRAPH_FILE, LEVEL_MAP_FILE,
          user.getLevelsUnlocked());

      dynamicNodes.put("username-label", username);
      dynamicNodes.put("level-selector-tool", lst);
      dynamicNodes.put("level-progress-bar", lpb);
    }

    loadLayout();
  }

  public void loadLevel() {

    Pane loadingPane = new LoadingPane(this);
    this.getChildren().add(loadingPane);

    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), loadingPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.setCycleCount(1);

    fadeIn.play();

    fadeIn.setOnFinished((e) -> {
      controller.initializeNewLevel(lst.getSelected());
      this.getChildren().remove(loadingPane);
    });
  }

}
