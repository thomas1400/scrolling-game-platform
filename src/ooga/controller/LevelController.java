package ooga.controller;

import java.io.FileNotFoundException;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import ooga.engine.loop.LevelLoop;
import ooga.exceptions.ExceptionFeedback;
import ooga.controller.data.BasicLevel;
import ooga.controller.data.CompleteLevel;
import ooga.controller.data.User;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import ooga.view.GameScreen;

public class LevelController implements Communicable{

  private User myUser;
  private int myLivesRemaining;
  private boolean levelLifeGainAllowed;

  private LevelLoop myLevelLoop;
  private Group myVisualGroup = new Group();

  public LevelController(GameScreen gs, User user, BasicLevel basicLevel) {
    myUser = user;
    CompleteLevel completeLevel = getCompleteLevel(gs, basicLevel);
    setLivesRemaining(completeLevel.getDeathsAllowed());
    levelLifeGainAllowed = completeLevel.getLifeGainAllowed();

    myLevelLoop = new LevelLoop(
        this, completeLevel, gs.getGameHeight(), gs.getGameWidth());

    EntityList visibleEntityList = myLevelLoop.getInitialVisibleEntityList();
    myVisualGroup.getChildren().addAll(visibleEntityList.getAsList());

    gs.setVisibleGroup(myVisualGroup);
  }

  private void setLivesRemaining(int deathsAllowed) {
    if (deathsAllowed < myUser.getLives()) {
      myLivesRemaining = deathsAllowed;
      myUser.adjustLives(-1 * deathsAllowed);
    } else {
      myLivesRemaining = myUser.getLives();
      myUser.adjustLives(-1 * myUser.getLives());
    }
  }

  private CompleteLevel getCompleteLevel(GameScreen gs, BasicLevel basicLevel) {
    CompleteLevel completeLevel = null;
    try {
      completeLevel = LevelBuilder.buildCompleteLevel(basicLevel, gs.getGameHeight(),
          gs.getGameWidth());
    } catch (FileNotFoundException e) {
      ExceptionFeedback.throwBreakingException(e, "File not found");
    }
    return completeLevel;
  }

  //Entity Visualization Handling
  @Override
  public void addEntity(Entity entity) {
    myVisualGroup.getChildren().add(entity);
  }
  @Override
  public void removeEntity(Entity entity) {
    myVisualGroup.getChildren().remove(entity);
  }
  @Override
  public void addAllEntities(EntityList entities) {
    myVisualGroup.getChildren().addAll(entities.getAsList()); }
  @Override
  public void removeAllEntities(EntityList entities) {
    myVisualGroup.getChildren().removeAll(entities.getAsList());  }

  //KeyPress Handling
  public void handleKeyPressed(KeyEvent keyEvent){
    myLevelLoop.processKeyPress(keyEvent);
  }
  public void handleKeyReleased(KeyEvent keyEvent) { myLevelLoop.processKeyRelease(keyEvent); }

  //LevelLoop State Handling
  public void beginLevel() {
    myLevelLoop.begin();
  }
  public void pauseLevel() {
    myLevelLoop.pause();
  }
  public void resumeLevel() {
    myLevelLoop.resume();
  }

  public void endLevel() {
    myLevelLoop.end();
    //TODO: Saving things
  }

  //User & Level Effect Handling
  public void handleWin() {

  }
  public void adjustPoints(int pointsAdjustment) {
    myUser.adjustPoints(pointsAdjustment);
    checkNewLife();
  }

  private void checkNewLife() {
    if (myUser.checkPointsToLife() && levelLifeGainAllowed){
      myLivesRemaining += 1;
    }
  }

  public void adjustLives(int lifeAdjustment) {
    if (levelLifeGainAllowed) {
      myLivesRemaining += lifeAdjustment;
    }
    myUser.adjustLives(lifeAdjustment);
  }

}
