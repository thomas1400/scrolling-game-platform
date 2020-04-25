package ooga.view.screen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.util.Pair;
import ooga.controller.ScreenController;
import ooga.exceptions.ExceptionFeedback;
import ooga.view.dynamicUI.GameSelector;

/**
 * A Screen that presents Game options to the user and lets them select a game.
 */
public class GameSelectionScreen extends Screen {

  private static final String RESOURCES_PATH = "games";
  private GameSelector gs;

  /**
   * Initializes a GameSelectionScreen, called with REFLECTION.
   * @param controller ScreenController
   */
  public GameSelectionScreen(ScreenController controller) {
    super(controller);

    gs = new GameSelector(getGamePairs());
    dynamicNodes.put("game-selector", gs);

    loadLayout();
  }

  private Map<String, Pair<String, Image>> getGamePairs() {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PATH);
    Map<String, Pair<String, Image>> pairs = new HashMap<>();

    for (String gameType : Collections.list(resources.getKeys())) {
      try {
        String title = resources.getString(gameType);
        String imagePath = "data/gamedata/"+gameType+"/gameIcon.png";
        pairs.put(gameType, new Pair<>(title, new Image(new FileInputStream(imagePath))));
      } catch (FileNotFoundException e) {
        ExceptionFeedback.throwHandledException(e,
            "Game icon not found for game: " + gameType + ".");
      }
    }

    return pairs;
  }

  /**
   * Passes the selected game to the ScreenController, called by REFLECTION.
   */
  public void select() {
    controller.setGame(gs.getSelected());
  }


}
