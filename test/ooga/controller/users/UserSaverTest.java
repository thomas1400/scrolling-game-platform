package ooga.controller.users;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Test;

class UserSaverTest {

  @Test
  void saveUser() {

    User testUser = UserFactory.getUser(new File("data/userdata/ALL UNLOCKED.user"));
    assert testUser != null;
    int initialPoints = testUser.getPoints();

    testUser.adjustPoints(150);
    UserSaver.saveUser(testUser);

    testUser = UserFactory.getUser(new File("data/userdata/ALL UNLOCKED.user"));
    assert testUser != null;
    assertEquals(testUser.getPoints(), initialPoints + 150);

  }
}