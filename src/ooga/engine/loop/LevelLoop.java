package ooga.engine.loop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ooga.engine.manager.CameraManager;
import ooga.engine.manager.CollisionManager;
import ooga.engine.manager.EntityManager;
import ooga.engine.manager.InputManager;
import ooga.model.data.Level;
import ooga.model.data.User;
import ooga.model.entity.EntityList;

public class LevelLoop implements Loopable {

  private EntityManager myEntityManager;
  private CameraManager myCameraManager;
  private InputManager myInputManager;
  private CollisionManager myCollisionManager;
  private EntityList myEntities;
  private Level myLevel;
  private User myUser;
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private Timeline myTimeline;

  public LevelLoop() {
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      loop();
    });
    myTimeline = new Timeline();
    myTimeline.setCycleCount(Timeline.INDEFINITE);
    myTimeline.getKeyFrames().add(frame);
    begin();


  }

  private void loop(){
    processInput();
    manageCollisions();
    updateEntities();
    updateCamera();

  }

  private void processInput() {}
  private void manageCollisions(){}
  private void updateEntities(){ }
  private void updateCamera(){}

  public void begin() {
    myTimeline.play();

  }

  public void end() {
    myTimeline.stop();

  }

  public void pause(){
    myTimeline.pause();
  }

  public void resume() {
    myTimeline.play();

  }

  public void exit() {

  }
}
