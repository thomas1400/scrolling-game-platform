package ooga.controller;

import java.io.FileNotFoundException;
import javafx.scene.Group;
import ooga.engine.loop.LevelLoop;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.data.Level;
import ooga.model.data.User;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import ooga.view.GameScreen;

public class LevelController implements Communicable{

  private User myUser;

  private Group myVisualGroup = new Group();

  public LevelController(GameScreen gs, User user, String levelName) {
    myUser = user;
    Level level = null;
    try {
      level = LevelBuilder.buildLevel(levelName);
    } catch (ExceptionFeedback | FileNotFoundException exceptionFeedback) {
      exceptionFeedback.printStackTrace();
    }
    assert level != null;
    LevelLoop myLevelLoop = new LevelLoop(this, level.getEntities());
    EntityList visibleEntityList = myLevelLoop.getInitialVisibleEntityList();
    myVisualGroup.getChildren().addAll(visibleEntityList.getAsList());
    gs.setVisibleGroup(myVisualGroup);
  }

  @Override
  public void addEntity(Entity entity) {
    myVisualGroup.getChildren().add(entity);
  }

  @Override
  public void removeEntity(Entity entity) {
    myVisualGroup.getChildren().remove(entity);
  }

  public void begin() {
  }

  public void handleUserInput(){


  }

  //TODO: Add ability for LevelLoop to pass up events that could effect the user (ex: extra life
  // or addition of coins etc).
}
