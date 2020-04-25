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

  private LevelSelectorTool lst;

  public LevelSelectorScreen(ScreenController controller, BasicLevelList levels) {
    super(controller);
    if (levels != null && controller.getUsers() != null) {

      String gameType = levels.getGameType();
      String myLevelGraphFile = "data/gamedata/" + gameType + "/levels/resources/LevelGraph.txt";
      String myLevelMapFile = "data/gamedata/" + gameType + "/levels/resources/LevelMap.txt";

      User user = controller.getUsers().getSelectedUser();

      DynamicUserLabel username = new DynamicUserLabel();
      username.setText(resources.getString("user") + " : " + user.getName());
      LevelProgressBar lpb = new LevelProgressBar(resources.getString("progress"),
          user.getLevelsCompleted(gameType).size(), levels.size());
      lst = new LevelSelectorTool(levels, myLevelGraphFile, myLevelMapFile,
          new ArrayList<>(user.getLevelsCompleted(gameType)));

      dynamicNodes.put("username-label", username);
      dynamicNodes.put("level-selector-tool", lst);
      dynamicNodes.put("level-progress-bar", lpb);

      loadLayout();
    }
  }

  public void loadLevel() {
    controller.initializeNewLevel(lst.getSelected());
  }

}
