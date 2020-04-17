package ooga.engine.loop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import ooga.controller.UpdateGame;
import ooga.controller.data.CompleteLevel;
import ooga.engine.manager.CameraManager.CameraManager;
import ooga.engine.manager.CollisionManager;
import ooga.engine.manager.EntityManager;
import ooga.engine.manager.InputManager;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class LevelLoop implements Loopable {

  private UpdateGame myLevelController;
  private EntityManager myEntityManager;
  private CameraManager myCameraManager;
  private InputManager myInputManager;
  private CollisionManager myCollisionManager;
  private Entity mainEntity;
  private EntityList myVisibleEntities;
  private static final int FRAMES_PER_SECOND = 600;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private Timeline myTimeline;
  private Object KeyEvent;

  public LevelLoop(UpdateGame levelController, CompleteLevel level, double screenHeight, double screenWidth) {
    System.out.println(screenWidth + " " + screenHeight);
    myLevelController = levelController;
    EntityList myEntities = level.getEntities();
    myEntityManager = new EntityManager(myEntities);
    myCameraManager = new CameraManager(level.getMainEntity(), screenHeight, screenWidth, level.getScrollType(), myEntities);
    myInputManager = new InputManager(level.getMainEntity());
    myCollisionManager = new CollisionManager();
    EntityList entitiesOnScreen = myCameraManager.initializeActiveEntities(myEntities);
    myVisibleEntities =  entitiesOnScreen;
    mainEntity = level.getMainEntity();
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
    reinitializeEntities();
    manageCollisions();
    updateEntities();
    processInput();
    updateCamera();
    updateScoreAndLives();
    sendEntities();
  }

  public void reinitializeEntities(){
    myEntityManager.initializeEntityLists();
    myCameraManager.initializeActivationStorage();
  }

  public void processInput(){
    myInputManager.processInput();
  }

  private void manageCollisions() {
    myCollisionManager.manageCollisions(myCameraManager.getOnScreenEntities());
    myEntityManager.manageEntities(myCollisionManager.getEntitiesReceived());
  }

  private void updateEntities() {
    for(Entity entity: myCameraManager.getOnScreenEntities()){
      entity.updateVisualization();
    }
  }

  private void updateCamera() {
    myCameraManager.updateCamera(myEntityManager.getEntities());
    if(myCameraManager.getActivatedEntities().size()!=0) {
      myEntityManager.addNewEntities(myCameraManager.getActivatedEntities());
    }
    if(myCameraManager.getDeactivatedEntities().size()!=0) {
      myEntityManager.removeOldEntities(myCameraManager.getDeactivatedEntities());
    }
  }

  private void updateScoreAndLives(){
    //myLevelController.adjustPoints(mainEntity.getScore);
    myLevelController.adjustPoints((int) mainEntity.getScore());
    System.out.println(mainEntity.getScore());
    //mainEntity.setScore(0);
    if(mainEntity.endedLevel()) {
      end();
      if (mainEntity.isSuccess()) {
        myLevelController.handleWin();
        end();
      }
      else{
        myLevelController.adjustLives(-1);
      }
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

  public void processKeyPress(KeyEvent keyEvent) {
    myInputManager.handleKeyPress(keyEvent);
  }
  public void processKeyRelease(KeyEvent keyEvent) { myInputManager.handleKeyRelease(keyEvent);
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
