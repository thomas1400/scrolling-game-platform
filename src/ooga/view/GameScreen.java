package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ooga.controller.LevelController;
import ooga.controller.ScreenController;

public class GameScreen extends Screen {

  private LevelController levelController;

  public GameScreen(ScreenController controller) {
    super(controller);
    initializeLayout();
  }

  private void initializeLayout() {
    VBox layout = new VBox();
    this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

    HBox infoBar = new HBox();
    infoBar.setPrefHeight(50);
    infoBar.setPrefWidth(Double.MAX_VALUE);

    HBox livesBox = new HBox();
    livesBox.setMinSize(350, 50);
    livesBox.setAlignment(Pos.TOP_LEFT);
    Label lives = new Label("Lives: ");
    lives.setFont(new Font(FONT_FAMILY, 20));
    lives.setTextFill(Color.WHITE);
    livesBox.getChildren().add(lives);
    infoBar.getChildren().add(livesBox);

    HBox scoreBox = new HBox();
    scoreBox.setMinSize(350, 50);
    scoreBox.setAlignment(Pos.TOP_RIGHT);
    Label score = new Label("Score: ");
    score.setFont(new Font(FONT_FAMILY, 20));
    score.setTextFill(Color.WHITE);
    scoreBox.getChildren().add(score);
    infoBar.getChildren().add(scoreBox);

    infoBar.getChildren().add(score);

    layout.getChildren().add(infoBar);

    HBox filler = new HBox();
    filler.setPrefHeight(100);
    filler.setPrefWidth(Double.MAX_VALUE);
    layout.getChildren().add(filler);

    HBox menuBar = new HBox();
    menuBar.setPrefHeight(50);
    menuBar.setMinHeight(50);
    menuBar.setPrefWidth(Double.MAX_VALUE);
    layout.getChildren().add(menuBar);

    this.getChildren().add(layout);
  }

  public void giveLevelController(LevelController lc) {
    this.levelController = lc;
  }

}
