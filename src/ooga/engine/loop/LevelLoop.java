package ooga.engine.loop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import ooga.controller.GameLevel;
import ooga.controller.levels.CompleteLevel;
import ooga.controller.Handleable;
import ooga.engine.manager.CameraManager.CameraManager;
import ooga.engine.manager.CollisionManager;
import ooga.engine.manager.EntityManager;
import ooga.engine.manager.InputManager;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * Creates a timeline for the game
 * Loops through anything that could impact the entities in the game including handling user input from the keyboard, handling collisions, adding and removing entities that result from a change in the camera view or the death or creation of a character, and communicating with the specific entities and the basicLevel controller
 * This class depends on receiving initial entities from the LevelController and depends on the entities to update properly to understand when points or lives should be altered and when the level should end
 * @author Cayla Schuval
 */
public class LevelLoop implements Loopable, Handleable {

  private GameLevel myLevelController;
  private EntityManager myEntityManager;
  private CameraManager myCameraManager;
  private InputManager myInputManager;
  private CollisionManager myCollisionManager;
  private EntityList myVisibleEntities;
  private static final int FRAMES_PER_SECOND = 600;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private Timeline myTimeline;
  private Entity mainEntity;

  /**
   * Creates instances of each manager class which are later used in the level loop
   * @param levelController GameLevel object that is used to communicate score updates, life updates, and status of level
   * @param level contains information regarding the scrolltype of the level and then main entity of the level
   * @param screenHeight height of the screen used to determine when entities move on and off the screen
   * @param screenWidth width of the screen used to determine wen entities move on and off the screen
   *
   */
  public LevelLoop(GameLevel levelController, CompleteLevel level, double screenHeight, double screenWidth) {
    myLevelController = levelController;
    mainEntity = level.getMainEntity();
    EntityList initialEntities = level.getEntities();
    myEntityManager = new EntityManager(initialEntities);
    myCameraManager = new CameraManager(screenHeight, screenWidth, level.getScrollType(), initialEntities);
    myInputManager = new InputManager(mainEntity, level.getGameType());
    myCollisionManager = new CollisionManager(mainEntity);
    myVisibleEntities = myCameraManager.initializeActiveEntities(initialEntities);
    myEntityManager.initializeEntityLists();
    createTimeline();
  }

  private void createTimeline() {
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      loop();
    });
    myTimeline = new Timeline();
    myTimeline.setCycleCount(Timeline.INDEFINITE);
    myTimeline.getKeyFrames().add(frame);
  }

  private void loop() {
    initializeEntityList();
    updateEntities();
    processInput();
    updateCamera();
    manageCollisions();
    updateGameStatsAndStatus();
    sendEntities();;
  }

  private void initializeEntityList(){
    myEntityManager.initializeEntityLists();
    myCameraManager.initializeActivationStorage();
  }
  private void updateEntities() {
    for(Entity entity: myCameraManager.getOnScreenEntities()){
      entity.updateVisualization();
    }
  }

  private void processInput(){
    myInputManager.processInput();
  }

  /**
   * Processes when a key is pressed that impacts the main entity
   * @param keyEvent event containing information regarding which key was pressed
   */
  public void handleKeyPressed(KeyEvent keyEvent) {
    myInputManager.handleKeyPress(keyEvent);
  }

  /**
   * Processes when a key is released that impacts the main entity
   * @param keyEvent event containing information regarding which key was released
   */
  public void handleKeyReleased(KeyEvent keyEvent) {
    myInputManager.handleKeyRelease(keyEvent);
  }

  private void updateCamera() {
    myCameraManager.updateCamera(myEntityManager.getEntities());
    if(myCameraManager.getActivatedEntities().size()!=0) {
      myEntityManager.entityMovedOnScreen(myCameraManager.getActivatedEntities());
    }
    if(myCameraManager.getDeactivatedEntities().size()!=0) {
      myEntityManager.entityMovedOffScreen(myCameraManager.getDeactivatedEntities());
    }
  }

  private void manageCollisions() {
    myCollisionManager.manageCollisions(myCameraManager.getOnScreenEntities());
    myEntityManager.manageEntitiesFromCollisions(myCollisionManager.getEntitiesReceived());
  }

  private void updateGameStatsAndStatus() {
    checkForPointUpdates();
    checkForLifeUpdates();
    checkIfLevelShouldEnd();
  }


  private void checkIfLevelShouldEnd() {
    if (mainEntity.endedLevel()) {
      myLevelController.handleWin();
    }
  }

  private void checkForLifeUpdates() {
    if (mainEntity.isDead()) {
      myLevelController.adjustLives(-1);
      mainEntity.revive();
    }
    if(mainEntity.getLives()>1){
      myLevelController.adjustLives((int) mainEntity.getLives()-1);
      mainEntity.setLives(1);
    }
  }

  private void checkForPointUpdates() {
    if (mainEntity.getScore() > 0) {
      myLevelController.adjustPoints((int) mainEntity.getScore());
      mainEntity.resetScore();
    }
  }


  private void sendEntities(){
    if(myEntityManager.getAddedEntities().size()!=0) {
      myLevelController.addAllEntities(myEntityManager.getAddedEntities());
    }
    if(myEntityManager.getRemovedEntities().size()!=0){
      myLevelController.removeAllEntities(myEntityManager.getRemovedEntities());
    }
  }

  /**
   * Method is called to begin the level and start the timeline
   */
  public void begin() {
    myTimeline.play();
  }

  /**
   * Method is called by LevelController to stop the timeline
   */
  public void end() {
    myTimeline.stop();
  }

  /**
   * Method is called by LevelController to pause the timeline
   */
  public void pause() {
    myTimeline.pause();
  }

  /**
   * Method is called by LevelController to resume the timeline
   */
  public void resume() {
    myTimeline.play();
  }

  /**
   * Method is called by LevelController to get the initial entities on the screen
   * After these initial entities are sent, only new entities are sent to the LevelController or entities that need to be removed
   */
  public EntityList getInitialVisibleEntityList() { return myVisibleEntities; }
}
