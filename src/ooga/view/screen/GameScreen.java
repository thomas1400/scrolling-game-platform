package ooga.view.screen;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ooga.controller.LevelController;
import ooga.controller.ScreenController;
import ooga.controller.data.BasicLevel;
import ooga.controller.data.User;

public class GameScreen extends Screen {

  private LevelController levelController;
  private Group gameGroup;
  private Rectangle gameBackground;
  private User user;
  private Button pause, resume;

  public GameScreen(ScreenController controller, BasicLevel level) {
    super(controller);
    user = controller.getUsers().getSelectedUser();

    gameGroup = new Group();
    gameBackground = new Rectangle();
    gameBackground.setFill(new ImagePattern(new Image(level.getBackgroundImage())));

    Label lives = new Label(resources.getString("lives") + ": " + user.getLives());
    lives.getStyleClass().add("user-data-display");
    Label score = new Label(resources.getString("score") + ": " + user.getPoints());
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

  public void setLevelController(LevelController lc) {
    levelController = lc;
  }

  public void setVisibleGroup(Group visibleGroup){
    gameGroup.getChildren().clear();
    gameGroup.getChildren().addAll(visibleGroup);
  }

  public void pause() {
    levelController.pauseLevel();
    pause.setDisable(true);
    resume.setDisable(false);
  }

  public void resume() {
    levelController.resumeLevel();
    pause.setDisable(false);
    resume.setDisable(true);
  }

  public void quit() {
    levelController.endLevel();
    handleButtonPress("exit");
  }

  public void reset() {
    levelController.resetLevel();
  }

  public double getGameWidth() {
    return gameBackground.getWidth();
  }

  public double getGameHeight() {
    return gameBackground.getHeight();
  }

}
