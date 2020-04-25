package ooga.view.dynamicUI;

import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * A custom-built UI control for selecting a game type from a menu.
 *
 * Used in the first screen of the application, allowing the user to select a game type.
 *
 * To use in a layout, add the dynamicUI package and put an instance in the Screen's
 * dynamicNodes map. Layout in FXLR using the dynamicNodes tag.
 */
public class GameSelector extends Selector {

  private Map<String, Pair<String, Image>> games;

  /**
   * Creates a game selector with the given games as options, where each entry in games
   * is a String, the game path, mapped to the game's name and icon.
   *
   * @param games map of game path to Pair of name and icon
   */
  public GameSelector(Map<String, Pair<String, Image>> games) {
    this.games = games;

    initializeButtons();
    this.getStyleClass().add("game-selector");
    selectionPane.getStyleClass().add("games");
  }

  protected void initializeButtons() {

    setToggleSize();

    for (String key : games.keySet()) {

      ImageView image = makeToggleGraphic(games.get(key).getValue());
      ToggleButton button = makeToggleButton(key, image);
      Label gameName = makeToggleLabel(games.get(key).getKey(), button);
      gameName.getStyleClass().add("game-select-toggle");

      makeButtonBox(button, gameName);
    }
  }

  /**
   * Gets the selected game.
   * @return selected
   */
  public String getSelected() {
      Toggle selected = toggles.getSelectedToggle();
      if (selected != null) {
        return ((ToggleButton) selected).getId();
      } else {
        return null;
      }
    }

}
