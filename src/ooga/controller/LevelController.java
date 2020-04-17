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
import ooga.view.screen.GameScreen;

public class LevelController implements Communicable{

  private User myUser;
  private int myLivesRemaining;
  private int myLevelNumber;
  private boolean levelLifeGainAllowed;

  private GameScreen myGS;
  private CompleteLevel myCompleteLevel;
  private CompleteLevel myInitialLevelState;

  private LevelLoop myLevelLoop;
  private Group myVisualGroup = new Group();

  public LevelController(GameScreen gs, User user, BasicLevel basicLevel) {
    myGS = gs;
    myUser = user;

    myLevelNumber = basicLevel.getLevelIndex();
    myCompleteLevel = getCompleteLevel(gs, basicLevel);
    myInitialLevelState = myCompleteLevel;

    setLivesRemaining(myCompleteLevel.getDeathsAllowed());
    levelLifeGainAllowed = myCompleteLevel.getLifeGainAllowed();

    myLevelLoop = createLevelLoop(myCompleteLevel);

    EntityList visibleEntityList = myLevelLoop.getInitialVisibleEntityList();
    myVisualGroup.getChildren().addAll(visibleEntityList.getAsList());

    gs.setVisibleGroup(myVisualGroup);
  }

  private LevelLoop createLevelLoop(CompleteLevel level) {
    return new LevelLoop(
        this, level, myGS.getGameHeight(), myGS.getGameWidth());
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

  private void setLivesRemaining(int deathsAllowed) {
    if (deathsAllowed < myUser.getLives()) {
      myLivesRemaining = deathsAllowed;
      myUser.adjustLives(-1 * deathsAllowed);
    } else {
      myLivesRemaining = myUser.getLives();
      myUser.adjustLives(-1 * myUser.getLives());
    }
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
    UserSaver.saveUser(myUser);
    deleteLevelLoop();
  }

  //In Game Adjustments
  public void adjustLives(int lifeAdjustment) {
    if (levelLifeGainAllowed) {
      myLivesRemaining += lifeAdjustment;
    }
    myUser.adjustLives(lifeAdjustment);
  }
  public void adjustPoints(int pointsAdjustment) {
    myUser.adjustPoints(pointsAdjustment);
    checkNewLife();
  }

  //User & Level Effect Handling
  public void handleWin() {
    //TODO: display some cool win screen?
    myUser.unlockNextLevel(myLevelNumber);
    myGS.quit();
  }

  private void deleteLevelLoop() {
    myLevelLoop = null;
    System.gc();
  }

  private void checkNewLife() {
    if (myUser.checkPointsToLife() && levelLifeGainAllowed){
      myLivesRemaining += 1;
    }
  }

}
