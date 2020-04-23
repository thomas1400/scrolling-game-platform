package ooga.controller;

import java.io.FileNotFoundException;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import ooga.engine.loop.LevelLoop;
import ooga.exceptions.ExceptionFeedback;
import ooga.controller.levels.BasicLevel;
import ooga.controller.levels.CompleteLevel;
import ooga.controller.users.User;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import ooga.view.screen.GameScreen;

public class LevelController implements GameLevel{

  private User myUser;
  private int myLivesRemaining;
  private String myGameType;
  private int myLevelNumber;
  private boolean levelLifeGainAllowed;

  private GameScreen myGS;

  private LevelLoop myLevelLoop;
  private Group myVisualGroup = new Group();

  public LevelController(GameScreen gs, User user, BasicLevel basicLevel) {
    myGS = gs;

    myUser = user;
    ifDeadGiveThreeLives();

    myLevelNumber = basicLevel.getLevelIndex();
    myGameType = basicLevel.getGameType();
    CompleteLevel myCompleteLevel = getCompleteLevel(basicLevel);

    setLivesRemaining(myCompleteLevel.getDeathsAllowed());
    levelLifeGainAllowed = myCompleteLevel.getLifeGainAllowed();

    myLevelLoop = createLevelLoop(myCompleteLevel);

    EntityList visibleEntityList = myLevelLoop.getInitialVisibleEntityList();
    myVisualGroup.getChildren().addAll(visibleEntityList.getAsList());

    gs.setVisibleGroup(myVisualGroup);
  }

  private void ifDeadGiveThreeLives() {
    if (myUser.getLives() <= 0){
      myUser.setLives(3);
    }
  }

  private LevelLoop createLevelLoop(CompleteLevel level) {
    return new LevelLoop(
        this, level, myGS.getGameHeight(), myGS.getGameWidth());
  }

  private CompleteLevel getCompleteLevel(BasicLevel basicLevel) {
    CompleteLevel completeLevel = null;
    try {
      completeLevel = LevelBuilder.buildCompleteLevel(basicLevel, myGS.getGameHeight(),
          myGS.getGameWidth());
    } catch (FileNotFoundException e) {
      ExceptionFeedback.throwBreakingException(e, "File not found");
    }
    return completeLevel;
  }

  private void setLivesRemaining(int deathsAllowed) {
    myLivesRemaining = Math.min(deathsAllowed, myUser.getLives());
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
    myGS.exit();
  }

  //In Game Adjustments
  public void adjustLives(int lifeAdjustment) {
    //System.out.println("LIFE ADJUST: " + lifeAdjustment);
    myLivesRemaining += lifeAdjustment;
    myUser.adjustLives(lifeAdjustment);
    checkEndLevel();
  }

  private void checkEndLevel() {
    if (myLivesRemaining == 0){
      endLevel();
    } else if (myLivesRemaining < 0){
      ExceptionFeedback.throwHandledException(new RuntimeException(), "Negative Lives Left in Level");
    }
  }

  public void adjustPoints(int pointsAdjustment) {
    System.out.println("POINTS: " + pointsAdjustment);
    myUser.adjustPoints(pointsAdjustment);
    checkNewLife();
  }

  //User & Level Effect Handling
  public void handleWin() {
    //TODO: display some cool win screen?
    myUser.unlockNextLevel(myGameType, myLevelNumber);
    endLevel();
  }

  private void deleteLevelLoop() {
    myLevelLoop = null;
    System.gc();
  }

  private void checkNewLife() {
    if (myUser.canConvertPointsToLife() && levelLifeGainAllowed){
      myLivesRemaining += 1;
    }
  }

}
