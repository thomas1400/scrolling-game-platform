package ooga.engine.loop;

import java.awt.event.KeyListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ooga.controller.Communicable;
import ooga.controller.LevelController;
import ooga.engine.manager.CameraManager;
import ooga.engine.manager.CollisionManager;
import ooga.engine.manager.EntityManager;
import ooga.engine.manager.InputManager;
import ooga.model.data.Level;
import ooga.model.data.User;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class LevelLoop implements Loopable, KeyListener {

  private Communicable myLevelController;
  private EntityManager myEntityManager;
  private CameraManager myCameraManager;
  private InputManager myInputManager;
  private CollisionManager myCollisionManager;
  private EntityList myEntities;
  private EntityList myVisibleEntities;
  private Level myLevel;
  private User myUser;
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private Timeline myTimeline;

  public LevelLoop(Communicable levelController, EntityList EntityList) {
    myEntities = EntityList;
    myLevelController = levelController;
    myEntityManager = new EntityManager();
    myCameraManager = new CameraManager(myEntities.getMainCharacter());
    myInputManager = new InputManager();
    myCollisionManager = new CollisionManager();
    myVisibleEntities = myCameraManager.activateEntities(myEntities);
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
    processInput();
    manageCollisions();
    updateEntities();
    // tell the entities to update gravity and stuff
    updateCamera();
  }

  private void processInput() {

  }

  private void manageCollisions() {
  }

  private void updateEntities() {
    //myEntityManager.
  }

  private void updateCamera() {
    myCameraManager.updateCamera(myEntities);
    EntityList activatedEntities = myCameraManager.getActivatedEntities();
    EntityList deactivatedEntities = myCameraManager.getDeactivatedEntities();
    sendEntitiesToController(activatedEntities, deactivatedEntities);
  }

  private void sendEntitiesToController(EntityList activatedEntities, EntityList deactivedEntities) {
    for(Entity entity: activatedEntities){
      myLevelController.addEntity(entity);
  }
    for(Entity entity: deactivedEntities){
      myLevelController.removeEntity(entity);
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

  public void exit() {
    myTimeline.stop();
  }

  public EntityList getInitialVisibleEntityList() {
  return myVisibleEntities;
  }

  public void keyTyped(KeyEvent e) {

  }

  public void keyPressed(KeyEvent e) {
    System.out.println("hi");

  }

  public void keyReleased(KeyEvent e) {
    System.out.println("bye");

  }
}
