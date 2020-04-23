package ooga.view.screen;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.users.User;
import ooga.view.dynamicUI.DynamicUserLabel;
import ooga.view.dynamicUI.LevelProgressBar;
import ooga.view.dynamicUI.LevelSelectorTool;

public class LevelSelectorScreen extends Screen {

  private static final String LEVEL_GRAPH_FILE = "resources/levels/resources/LevelGraph.txt";
  private static final String LEVEL_MAP_FILE = "resources/levels/resources/LevelMap.txt";

  private BasicLevelList myLevels;
  private LevelSelectorTool lst;

  public LevelSelectorScreen(ScreenController controller, BasicLevelList levels) {
    super(controller);
    myLevels = levels;

    if (controller.getUsers() != null) {
      User user = controller.getUsers().getSelectedUser();

      DynamicUserLabel username = new DynamicUserLabel();
      username.setText(resources.getString("user") + " : " + user.getName());
      //FIXME: The "mario" should load in only the level selector for Mario levels, using
      // "flappy" or "doodle" should load in the other levels. I assume eventually you'll call
      // this in a loop for all level types, which I envision being passed to you slightly
      // differently in your constructor... possibly a Map of levels with keys corresponding to
      // their string level type. Lmk what you think -from Grant
      final String GAME_TYPE = "gamedata/mario";
      LevelProgressBar lpb = new LevelProgressBar(resources.getString("progress"),
          user.getLevelsCompleted(GAME_TYPE).size(), myLevels.size());
      lst = new LevelSelectorTool(levels, LEVEL_GRAPH_FILE, LEVEL_MAP_FILE,
          new ArrayList<>(user.getLevelsCompleted(GAME_TYPE)));


      dynamicNodes.put("username-label", username);
      dynamicNodes.put("level-selector-tool", lst);
      dynamicNodes.put("level-progress-bar", lpb);
    }

    loadLayout();
  }

  public void loadLevel() {

    Pane loadingPane = new LoadingScreen(this, lst.getSelected());
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
