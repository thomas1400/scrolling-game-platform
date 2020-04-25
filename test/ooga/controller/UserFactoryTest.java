package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashSet;
import javafx.stage.Stage;
import ooga.controller.users.User;
import ooga.controller.users.UserFactory;
import ooga.controller.users.UserSaver;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class UserFactoryTest {

  @Test
  void getUser() {
    User testUser = UserFactory.getUser(new File("data/userdata/ALL UNLOCKED.user"));

    assert testUser != null;
    assertEquals(testUser.getName(), "ALL UNLOCKED");
  }

  @Test
  void getDefaultUser() {
    User defaultUser = UserFactory.getDefaultUser();

    assertEquals("Default User", defaultUser.getName());
    assertEquals(3, defaultUser.getLives());
    assertTrue(defaultUser.getAllGames().contains("mario"));
    assertTrue(defaultUser.getAllGames().contains("flappy"));
    assertTrue(defaultUser.getAllGames().contains("doodlejump"));
    assertTrue(defaultUser.getAllGames().contains("dinorun"));

    HashSet<Integer> defaultLevelsUnlocked = new HashSet<>();
    defaultLevelsUnlocked.add(0);

    assertEquals(defaultLevelsUnlocked, defaultUser.getLevelsCompleted("mario"));
    assertEquals(defaultLevelsUnlocked, defaultUser.getLevelsCompleted("flappy"));
    assertEquals(defaultLevelsUnlocked, defaultUser.getLevelsCompleted("doodlejump"));
    assertEquals(defaultLevelsUnlocked, defaultUser.getLevelsCompleted("dinorun"));
  }
}