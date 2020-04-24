package ooga.engine.loop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import ooga.controller.GameLevel;
import ooga.controller.levels.CompleteLevel;
import ooga.engine.manager.CameraManager.CameraManager;
import ooga.engine.manager.CollisionManager;
import ooga.engine.manager.EntityManager;
import ooga.engine.manager.InputManager;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class LevelLoop implements Loopable {

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

  public LevelLoop(GameLevel levelController, CompleteLevel level, double screenHeight, double screenWidth) {
    myLevelController = levelController;
    mainEntity = level.getMainEntity();
    EntityList initialEntities = level.getEntities();
    myEntityManager = new EntityManager(initialEntities);
    myCameraManager = new CameraManager(screenHeight, screenWidth, level.getScrollType(), initialEntities);
    myInputManager = new InputManager(mainEntity, level.getGameType());
    myCollisionManager = new CollisionManager(level.getGameType());
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

  public void initializeEntityList(){
    myEntityManager.initializeEntityLists();
    myCameraManager.initializeActivationStorage();
  }
  private void updateEntities() {
    for(Entity entity: myCameraManager.getOnScreenEntities()){
      entity.updateVisualization();
    }
  }

  public void processInput(){
    myInputManager.processInput();
  }

  public void processKeyPress(KeyEvent keyEvent) {
    myInputManager.handleKeyPress(keyEvent);
  }

  public void processKeyRelease(KeyEvent keyEvent) { myInputManager.handleKeyRelease(keyEvent);
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

  public void begin() {
    myTimeline.play();
  }

  public void end() {
    myTimeline.stop();
  }

  public void pause() {
    myTimeline.pause();
  }

  public void resume() {
    myTimeline.play();
  }

  public EntityList getInitialVisibleEntityList() { return myVisibleEntities; }
}
