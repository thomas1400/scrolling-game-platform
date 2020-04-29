package ooga.view.screen;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ooga.controller.LevelController;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevel;
import ooga.controller.users.User;

public class GameScreen extends Screen {

  private LevelController levelController;
  private Group gameGroup;
  private Rectangle gameBackground;
  private Button pause, resume;

  /**
   * Initializes a GameScreen to display a game level.
   * @param controller ScreenController
   * @param level level to load
   */
  public GameScreen(ScreenController controller, BasicLevel level) {
    super(controller);
    User user = controller.getUsers().getSelectedUser();

    gameGroup = new Group();
    gameBackground = new Rectangle();
    gameBackground.setFill(new ImagePattern(new Image(level.getBackgroundImage())));

    Label lives = new Label();
    lives.textProperty().bind(user.getLivesProperty().asString(resources.getString("lives") + ": %d"));
    lives.getStyleClass().add("user-data-display");
    Label score = new Label();
    score.textProperty().bind(user.getPointsProperty().asString(resources.getString("score") + ": %d"));
    score.getStyleClass().add("user-data-display");
    pause = new Button();
    resume = new Button();
    resume.setDisable(true);

    dynamicNodes.put("game-group", gameGroup);
    dynamicNodes.put("game-background", gameBackground);
    dynamicNodes.put("lives-label", lives);
    dynamicNodes.put("score-label", score);
    dynamicNodes.put("pause", pause);
    dynamicNodes.put("resume", resume);

    loadLayout();
  }

  /**
   * Assigns a level controller to the GameScreen
   * @param lc LevelController
   */
  public void setLevelController(LevelController lc) {
    levelController = lc;
  }

  /**
   * Sets the visible group of game Nodes
   * @param visibleGroup Group of Nodes
   */
  public void setVisibleGroup(Group visibleGroup){
    gameGroup.getChildren().clear();
    gameGroup.getChildren().addAll(visibleGroup);
  }

  protected void pause() {
    levelController.pause();
    pause.setDisable(true);
    resume.setDisable(false);
  }

  /**
   * Used by REFLECTION and resume button to resume the game.
   */
  protected void resume() {
    levelController.resume();
    pause.setDisable(false);
    resume.setDisable(true);
  }

  /**
   * Used by LevelController to exit the game and fade in splash screens.
   */
  public void exit(boolean winState) {
    SplashScreen splash;
    if (winState) {
      splash = new LevelSuccessSplash(controller, this);
    } else {
      splash = new LevelFailedSplash(controller, this);
    }
    splash.setOpacity(0.0);
    this.getChildren().add(splash);

    FadeTransition fadePause = new FadeTransition(Duration.seconds(0.5), splash);
    fadePause.setFromValue(0);
    fadePause.setToValue(0);
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), splash);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    FadeTransition fadeHold = new FadeTransition(Duration.seconds(2), splash);
    fadeHold.setFromValue(1);
    fadeHold.setToValue(1);

    fadePause.setOnFinished(e-> {
      splash.setOpacity(1.0);
      fadeIn.play();
    });
    fadeIn.setOnFinished(e->fadeHold.play());
    fadeHold.setOnFinished(e->handleButtonPress("exit"));

    fadePause.play();
  }

  /**
   * Used by REFLECTION and quit button to quit the game.
   */
  protected void quit() {
    levelController.endLevel(false);
  }

  /**
   * Used by REFLECTION and reset button to reset the game.
   */
  protected void reset() {
    controller.restartLevel();
  }
//
//  /**
//   * Used by REFLECTION and settings button to open the settings menu.
//   */
//  protected void openSettings() {
//    pause();
//    SettingsScreen settings = new SettingsScreen(controller, this);
//    this.getChildren().add(settings);
//  }

  /**
   * Gets the width of the game background.
   * @return visible width
   */
  public double getGameWidth() {
    return gameBackground.getWidth();
  }

  /**
   * Gets the height of the game background.
   * @return visible height
   */
  public double getGameHeight() {
    return gameBackground.getHeight();
  }

}
