package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import javafx.stage.Stage;
import ooga.controller.users.User;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class UserFactoryTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @Test
  void getUser() {
  }

  @Test
  void getDefaultUser() {
    User defaultUser = UserFactory.getDefaultUser();

    assertEquals("Default User", defaultUser.getName());
    assertEquals(3, defaultUser.getLives());
    assertTrue(defaultUser.getAllGames().contains("gamedata/mario"));
    assertTrue(defaultUser.getAllGames().contains("gamedata/flappy"));
    assertTrue(defaultUser.getAllGames().contains("gamedata/doodle"));

    HashSet<Integer> defaultLevelsUnlocked = new HashSet<>();
    defaultLevelsUnlocked.add(0);

    assertEquals(defaultLevelsUnlocked, defaultUser.getLevelsCompleted("gamedata/mario"));
    assertEquals(defaultLevelsUnlocked, defaultUser.getLevelsCompleted("gamedata/flappy"));
    assertEquals(defaultLevelsUnlocked, defaultUser.getLevelsCompleted("gamedata/doodle"));
  }
}