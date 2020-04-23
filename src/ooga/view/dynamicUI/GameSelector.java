package ooga.view.dynamicUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import ooga.controller.users.User;

public class GameSelector extends Pane {

    private static final double SPACING = 10;
    private static final double TILES_PER_PAGE = 3;
    private static final double SCROLL_BAR_OFFSET = 50.0;
    private Map<String, Pair<String, Image>> games;
    private ToggleGroup gameToggles;
    private ScrollPane scrollPane;
    private HBox gamePane;

  public GameSelector(Map<String, Pair<String, Image>> games) {
      gameToggles = new ToggleGroup();
      scrollPane = new ScrollPane();
      gamePane = new HBox();
      gamePane.setAlignment(Pos.CENTER);
      gamePane.setSpacing(SPACING);
      scrollPane.setContent(gamePane);
      scrollPane.setFitToHeight(true);
      scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
      scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

      this.games = games;

      initializeButtons();
      this.getChildren().add(scrollPane);
      this.getStyleClass().add("game-selector");
      gamePane.getStyleClass().add("games");
    }

    private void initializeButtons() {
      double tileSize = this.getPrefWidth() / (TILES_PER_PAGE) - SPACING;

      for (String key : games.keySet()) {

        ImageView image = new ImageView(games.get(key).getValue());
        image.setFitHeight(tileSize);
        image.setFitWidth(tileSize);

        ToggleButton button = new ToggleButton();
        button.setGraphic(image);
        button.setId(key);
        button.setToggleGroup(gameToggles);
        button.setPrefSize(tileSize, tileSize);

        Label gameName = new Label(games.get(key).getKey());
        gameName.setPrefWidth(button.getPrefWidth());
        gameName.setAlignment(Pos.TOP_CENTER);
        gameName.getStyleClass().add("game-select-toggle");
        gameName.setMinHeight(SCROLL_BAR_OFFSET);

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(button);
        buttonBox.getChildren().add(gameName);
        gamePane.getChildren().add(buttonBox);
      }
    }

    public String getSelected() {
      Toggle selected = gameToggles.getSelectedToggle();
      if (selected != null) {
        return ((ToggleButton) selected).getId();
      } else {
        return null;
      }
    }

    public void setPaneWidth(double width) {
      setPrefWidth(width);
      reinitializeButtons();
      scrollPane.setPrefWidth(width);
    }

    public void setPaneHeight(double height) {
      setPrefHeight(height);
      reinitializeButtons();
    }

    private void reinitializeButtons() {
      gamePane.getChildren().clear();
      initializeButtons();
    }

}
