package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import javafx.stage.Stage;
import ooga.controller.levels.BasicLevel;
import ooga.controller.levels.CompleteLevel;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class LevelBuilderTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @Test
  void buildBasicLevel() {
    File levelFile = new File("data/gamedata/mario/levels/Training.level");
    BasicLevel myLevel = LevelBuilder.buildBasicLevel(0, levelFile);

    assertEquals(myLevel.getGameType(), "mario");
    assertEquals(myLevel.getMainTitle(), "Testing Level");
    assertEquals(myLevel.getSubTitle(), "Tutorial");
    assertEquals(myLevel.getLevelFile(), levelFile);
    assertEquals(myLevel.getBackgroundImage(), "gamedata/mario/levels/backgrounds/levelBackground.png");
  }

  @Test
  void buildCompleteLevel() {
    File levelFile = new File("data/gamedata/mario/levels/Training.level");
    BasicLevel basicLevel = LevelBuilder.buildBasicLevel(0, levelFile);
    CompleteLevel myLevel = LevelBuilder.buildCompleteLevel(basicLevel, 100, 100);

    assertEquals(myLevel.getGameType(), "mario");
    assertEquals(myLevel.getScrollType(), "horizontal");
    assertEquals(myLevel.getDeathsAllowed(), 1);
    assertFalse(myLevel.getLifeGainAllowed());

    Entity mainEntity = EntityBuilder.getEntity("Player","mario");
    assertEquals(myLevel.getMainEntity().getLives(), mainEntity.getLives());
  }
}