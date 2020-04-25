package ooga.view.screen;

import java.util.ArrayList;
import javafx.scene.control.Label;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.users.User;
import ooga.view.dynamicUI.LevelProgressBar;
import ooga.view.dynamicUI.LevelSelectorTool;

/**
 * Level Selector Screen that allows a user to select a level using the LevelSelectorTool.
 *
 * Displays a map of available levels, allows the user to select a level, then hit a button
 * to begin the game, opening the level.
 */
public class LevelSelectorScreen extends Screen {

  private static final String DATA_GAMEDATA = "data/gamedata/";
  private static final String LEVELS_RESOURCES = "/levels/resources/";
  private static final String LEVEL_GRAPH = "LevelGraph.txt";
  private static final String LEVEL_MAP = "LevelMap.txt";
  private LevelSelectorTool lst;

  /**
   * Initializes a LevelSelectorScreen, called via REFLECTION.
   * @param controller ScreenController
   * @param levels BasicLevelList of levels
   */
  public LevelSelectorScreen(ScreenController controller, BasicLevelList levels) {
    super(controller);
    if (levels != null && controller.getUsers() != null) {

      String gameType = levels.getGameType();
      String filePrefix = DATA_GAMEDATA + gameType + LEVELS_RESOURCES;
      String myLevelGraphFile = filePrefix + LEVEL_GRAPH;
      String myLevelMapFile = filePrefix + LEVEL_MAP;

      User user = controller.getUsers().getSelectedUser();

      Label username = new Label(resources.getString("user") + " : " + user.getName());
      username.setId("usernameLabel");
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

  /**
   * Called via REFLECTION, loads new level, passing selected level to controller.
   */
  protected void loadLevel() {
    controller.initializeNewLevel(lst.getSelected());
  }

}
