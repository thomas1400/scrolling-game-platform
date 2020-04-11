package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ooga.controller.LevelController;
import ooga.controller.ScreenController;
import ooga.model.data.User;
import ooga.view.factory.ControlFactory;

public class GameScreen extends Screen {

  private LevelController levelController;
  private Group gameGroup;
  private Rectangle gameBackground;
  private User user;

  public GameScreen(ScreenController controller) {
    super(controller);
    gameGroup = new Group();
    user = controller.getUsers().getSelectedUser();
    setWorkingDimensions(3, 1);
    initializeLayout();
  }

  private void initializeLayout() {
    ControlFactory cf = new ControlFactory(PADDING);
    VBox layout = new VBox();
    layout.setAlignment(Pos.CENTER);
    layout.setSpacing(PADDING);
    this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));

    HBox infoBar = new HBox();
    infoBar.setSpacing(0.25*workingWidth);
    infoBar.setAlignment(Pos.CENTER);
    infoBar.setPrefHeight(0.1*workingHeight);

    Label lives = cf.label(resources.getString("lives") + ": " + user.getLives(), BUTTON_FONT_SIZE);
    lives.setPrefHeight(infoBar.getPrefHeight());
    infoBar.getChildren().add(lives);

    Label score = cf.label(resources.getString("score") + ": " + user.getPoints(), BUTTON_FONT_SIZE);
    score.setPrefHeight(infoBar.getPrefHeight());
    infoBar.getChildren().add(score);

    layout.getChildren().add(infoBar);

    gameBackground = new Rectangle(workingWidth+2*PADDING, 0.8*workingHeight);
    gameBackground.setFill(Color.WHITE);
    layout.getChildren().add(gameBackground);

    HBox menuBar = new HBox();
    menuBar.setAlignment(Pos.CENTER);
    menuBar.setPrefHeight(0.1*workingHeight);
    menuBar.setSpacing(PADDING);
    layout.getChildren().add(menuBar);

    Button pause, resume;
    pause = cf.button(resources.getString("pause"), BUTTON_FONT_SIZE,
        e-> {}, 100, menuBar.getPrefHeight());
    pause.setFocusTraversable(false);
    resume = cf.button(resources.getString("resume"), BUTTON_FONT_SIZE,
        e-> {}, 100, menuBar.getPrefHeight());
    resume.setFocusTraversable(false);
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

    Button quit = cf.button(resources.getString("quit"), BUTTON_FONT_SIZE,
        e->handleButtonPress("quit"), 100, menuBar.getPrefHeight());
    quit.setFocusTraversable(false);
    menuBar.getChildren().add(quit);

    this.getChildren().add(layout);

    gameGroup.setTranslateX(0);
    gameGroup.setTranslateY(0.1*workingHeight + 4*PADDING);
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

  public void quit() {
    levelController.endLevel();
    handleButtonPress("exit");
  }

  public double getGameWidth() {
    return gameBackground.getWidth();
  }

  public double getGameHeight() {
    return gameBackground.getHeight();
  }

}
