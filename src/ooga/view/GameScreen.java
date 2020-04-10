package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ooga.controller.LevelController;
import ooga.controller.ScreenController;
import ooga.view.factory.ControlFactory;

public class GameScreen extends Screen {

  private LevelController levelController;
  private Group gameGroup;

  public GameScreen(ScreenController controller) {
    super(controller);
    gameGroup = new Group();
    setWorkingDimensions(3, 1);
    initializeLayout();
  }

  private void initializeLayout() {
    ControlFactory cf = new ControlFactory(PADDING);
    VBox layout = new VBox();
    this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));

    HBox infoBar = new HBox();
    infoBar.setPrefHeight(0.1*workingHeight);
    infoBar.setPrefWidth(workingWidth);

    Label lives = cf.label(resources.getString("lives"), BUTTON_FONT_SIZE);
    lives.setPrefSize(0.4*workingWidth, infoBar.getPrefHeight());
    infoBar.getChildren().add(lives);

    Label score = cf.label(resources.getString("score"), BUTTON_FONT_SIZE);
    score.setPrefSize(0.4*workingWidth, infoBar.getPrefHeight());
    infoBar.getChildren().add(score);

    layout.getChildren().add(infoBar);

    Rectangle gameBackground = new Rectangle(workingWidth, 0.8*workingHeight);
    VBox.setMargin(gameBackground, new Insets(10));
    gameBackground.setFill(Color.WHITE);
    layout.getChildren().add(gameBackground);

    HBox menuBar = new HBox();
    VBox.setMargin(menuBar, new Insets(PADDING));
    menuBar.setPrefHeight(0.1*workingHeight);
    menuBar.setPrefWidth(Double.MAX_VALUE);
    menuBar.setSpacing(PADDING);
    layout.getChildren().add(menuBar);

    Button pause, resume;
    pause = cf.button(resources.getString("pause"), BUTTON_FONT_SIZE,
        e-> {}, 100, menuBar.getPrefHeight());
    resume = cf.button(resources.getString("resume"), BUTTON_FONT_SIZE,
        e-> {}, 100, menuBar.getPrefHeight());
    resume.setDisable(true);

    pause.setOnAction(e-> {
      handleButtonPress("pause");
      pause.setDisable(true);
      resume.setDisable(false);
    });
    resume.setOnAction(e-> {
      handleButtonPress("resume");
      pause.setDisable(false);
      resume.setDisable(true);
    });
    menuBar.getChildren().add(pause);
    menuBar.getChildren().add(resume);

    this.getChildren().add(layout);
    this.getChildren().add(gameGroup);
  }

  public void setLevelController(LevelController lc) {
    levelController = lc;
  }

  public void setVisibleGroup(Group visibleGroup){
    gameGroup.getChildren().clear();
    gameGroup.getChildren().addAll(visibleGroup);
  }

  public void pause() {
    levelController.pauseLevel();
  }

  public void resume() {
    levelController.resumeLevel();
  }

}
