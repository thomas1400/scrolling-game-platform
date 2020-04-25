package ooga.controller;

import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import ooga.engine.loop.LevelLoop;
import ooga.engine.loop.Loopable;
import ooga.exceptions.ExceptionFeedback;
import ooga.controller.levels.BasicLevel;
import ooga.controller.levels.CompleteLevel;
import ooga.controller.users.User;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import ooga.view.screen.GameScreen;

public class LevelController implements GameLevel, Loopable, Handleable {

  private User myUser;
  private int myLivesRemaining;
  private String myGameType;
  private int myLevelNumber;
  private boolean levelLifeGainAllowed;

  private GameScreen myGS;
  private LevelLoop myLevelLoop;
  private Group myVisualGroup = new Group();

  /**
   * Class used to control everything related to a single level. Coordinated with the created
   * level loop to adjust visuals, user stats, and coordinate related screens appropriately.
   *
   * @param gs GameScreen to be updated with visual entities
   * @param user User to have stats updated though the level
   * @param basicLevel BasicLevel used to load in initial entities and set level parameters
   */
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
    return LevelBuilder.buildCompleteLevel(basicLevel, myGS.getGameHeight(),
          myGS.getGameWidth());
  }

  private void setLivesRemaining(int deathsAllowed) {
    myLivesRemaining = Math.min(deathsAllowed, myUser.getLives());
  }

  //Entity Visualization Handling
  /**
   * @param entity to be added to the visual group
   */
  public void addEntity(Entity entity) {
    myVisualGroup.getChildren().add(entity);
  }

  /**
   * @param entity to be removed from the visual group
   */
  public void removeEntity(Entity entity) {
    myVisualGroup.getChildren().remove(entity);
  }

  /**
   * @param entities to be added to the visual group
   */
  public void addAllEntities(EntityList entities) {
    myVisualGroup.getChildren().addAll(entities.getAsList());
  }

  /**
   * @param entities to be removed from the visual group
   */
  public void removeAllEntities(EntityList entities) {
    myVisualGroup.getChildren().removeAll(entities.getAsList());  }

  //KeyPress Handling
  /**
   * @param keyEvent to handle the pressing of key presses to the LevelLoop
   */
  public void handleKeyPressed(KeyEvent keyEvent){
    myLevelLoop.handleKeyPressed(keyEvent);
  }

  /**
   * @param keyEvent to handle the releasing of key presses to the LevelLoop
   */
  public void handleKeyReleased(KeyEvent keyEvent) { myLevelLoop.handleKeyReleased(keyEvent); }

  //LevelLoop State Handling
  /**
   * begins the level loop
   */
  public void begin() {
    myLevelLoop.begin();
    myLevelLoop.pause();
  }

  /**
   * pauses the level loop
   */
  public void pause() {
    myLevelLoop.pause();
  }

  /**
   * resumes the level loop
   */
  public void resume() {
    myLevelLoop.resume();
  }

  /**
   * ends the level loop, saves users, and switches back to the level selector screen
   */
  public void endLevel(boolean winState){
    myLevelLoop.end();
    UserSaver.saveUser(myUser);
    deleteLevelLoop();
    myGS.exit(winState);
  }

  //In Game Adjustments
  /**
   * Adjusts lives accordingly and checks for the end of a game
   * @param lifeAdjustment to be made to the lives per level and user lives
   */
  public void adjustLives(int lifeAdjustment) {
    myLivesRemaining += lifeAdjustment;
    myUser.adjustLives(lifeAdjustment);
    checkEndLevel();
  }

  /**
   * Adjusts points accordingly and checks for point to life conversion
   * @param pointsAdjustment to be made to the points in the level
   */
  public void adjustPoints(int pointsAdjustment) {
    myUser.adjustPoints(pointsAdjustment);
    checkNewLife();
  }

  /**
   * handles the game's win by unlocking the next level and prompting the end of the current level
   */
  public void handleWin() {
    myUser.unlockNextLevel(myGameType, myLevelNumber);
    endLevel(true);
  }

  private void checkEndLevel() {
    if (myLivesRemaining == 0){
      endLevel(false);
    } else if (myLivesRemaining < 0){
      ExceptionFeedback.throwHandledException(new RuntimeException(), "Negative Lives Left in Level");
    }
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
