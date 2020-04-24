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

public class GameSelectionScreen extends Screen {

  private static final String RESOURCES_PATH = "games";
  private static final char RESOURCE_DIVIDER = ',';
  private GameSelector gs;

  //CALLED REFLEXIVELY
  public GameSelectionScreen(ScreenController controller) {
    super(controller);

    gs = new GameSelector(getGamePairs());
    dynamicNodes.put("game-selector", gs);

    loadLayout();
  }

  private Map<String, Pair<String, Image>> getGamePairs() {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PATH);
    Map<String, Pair<String, Image>> pairs = new HashMap<>();

    for (String key : Collections.list(resources.getKeys())) {
      try {
        String resourceLine = resources.getString(key);
        String title = resourceLine.substring(0, resourceLine.indexOf(RESOURCE_DIVIDER));
        String imagePath = resourceLine.substring(resourceLine.indexOf(RESOURCE_DIVIDER) + 1);
        pairs.put(key, new Pair<>(title, new Image(new FileInputStream(imagePath))));
      } catch (FileNotFoundException e) {
        ExceptionFeedback.throwHandledException(e,
            "Game icon not found for game: " + key + ".");
      }
    }

    return pairs;
  }

  public void select() throws FileNotFoundException {
    controller.setGame(gs.getSelected());
  }


}
