package ooga.controller;

import java.io.FileNotFoundException;
import javafx.scene.Group;
import ooga.engine.loop.LevelLoop;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.data.Level;
import ooga.model.data.User;
import ooga.model.entity.Entity;
import ooga.view.GameScreen;

public class LevelController implements Communicable{

  private User myUser;

  private Group myVisualGroup = new Group();

  public LevelController(GameScreen gs, User user, String levelName) {
    myUser = user;
    try {
      Level level = LevelBuilder.buildLevel(levelName);
    } catch (ExceptionFeedback exceptionFeedback) {
      exceptionFeedback.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    //LevelLoop myLevelLoop = new LevelLoop(this, level.getEntities());
    //Entity List visibleEntityList = myLevelLoop.getInitialVisibleEntityList()
    //myVisualGroup.getChildren().addAll(visibleEntityList.asList());
    //gs.setVisibleGroup(myVisualGroup);
  }

  @Override
  public void addEntity(Entity entity) {
    myVisualGroup.getChildren().add(entity);
  }

  @Override
  public void removeEntity(Entity entity) {
    myVisualGroup.getChildren().remove(entity);
  }

  //TODO: Add ability for LevelLoop to pass up events that could effect the user (ex: extra life
  // or addition of coins etc).
}
